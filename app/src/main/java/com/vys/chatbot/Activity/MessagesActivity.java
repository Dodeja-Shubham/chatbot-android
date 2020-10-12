package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.vys.chatbot.Adapter.ChannelMessagesAdapter;
import com.vys.chatbot.Adapter.DMMessagesAdapter;
import com.vys.chatbot.Adapter.ScheduleArrayAdapter;
import com.vys.chatbot.Class.SlackApiRequestClass;
import com.vys.chatbot.Class.RecyclerItemClickListener;
import com.vys.chatbot.Models.ChannelInfoAPI.ChannelInfoAPI;
import com.vys.chatbot.Models.ChannelJoinAPI.ChannelJoinAPI;
import com.vys.chatbot.Models.ChannelMessagesAPI.ChannelMessagesAPI;
import com.vys.chatbot.Models.DMMessagesAPI.DMMessagesAPI;
import com.vys.chatbot.Models.DelMessageAPI;
import com.vys.chatbot.Models.SuccessResponse;
import com.vys.chatbot.Models.UserProfileAPI.UserProfileAPI;
import com.vys.chatbot.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vys.chatbot.Activity.MainActivity.ADMIN_TOKEN;
import static com.vys.chatbot.Activity.MainActivity.BOT_TOKEN;
import static com.vys.chatbot.Activity.MainActivity.USER_TOKEN;

public class MessagesActivity extends AppCompatActivity {

    private final String TAG = "MessagesActivity";

    private final String[] SCHEDULES = {"Once", "Daily", "Every 7 days","Every 30 days"};

    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(SlackApiRequestClass.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
    private SlackApiRequestClass retrofitCall = retrofit.create(SlackApiRequestClass.class);

    DMMessagesAPI userMessages;
    ChannelMessagesAPI channelMessages;

    boolean firstTym = true;

    RecyclerView messagesRV;
    MaterialRippleLayout backBtn, timer;
    TextView title;

    SharedPreferences prefs;

    ImageView sendBtn, sendBotBtn;
    EditText typedMessage;

    LinearLayout holder;

    String type = "", id = "", name = "", user = "";

    ChannelInfoAPI userChannelInfo, botChannelInfo;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        try {
            type = extras.getString("type", "");
            id = extras.getString("id", "");
            name = extras.getString("name", "");
            user = extras.getString("user", "");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        if (type.equals("") || id.equals("")) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }

        messagesRV = findViewById(R.id.messages_rv);
        backBtn = findViewById(R.id.messages_back);
        title = findViewById(R.id.messages_title);
        typedMessage = findViewById(R.id.messages_edit_text);
        sendBtn = findViewById(R.id.messages_send_btn);
        holder = findViewById(R.id.send_message_holder);
        sendBotBtn = findViewById(R.id.messages_send_bot_btn);
        timer = findViewById(R.id.messages_timer);

        sendBtn.setOnClickListener(it -> sendMessage());

        sendBotBtn.setOnClickListener(it -> {
            if (firstTym) {
                firstTym = false;
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                        .setTitle("Alert !!!")
                        .setMessage("This option will send messages as ChatBot. Users will not be able to know that messages are sent by you until you tell them.")
                        .addButton("OK", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                            dialog.dismiss();
                        });
                builder.show();
            } else {
                sendAsBot();
            }
        });

        backBtn.setOnClickListener(it -> super.onBackPressed());

        timer.setOnClickListener(it -> {
            Dialog dialog = new Dialog(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.schedule_message_dialog, null);
            dialog.setContentView(dialogView);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            assert window != null;
            window.setGravity(Gravity.CENTER);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            int[] selected = {0};
            EditText msg = dialogView.findViewById(R.id.schedule_message_et);
            CardView send = dialogView.findViewById(R.id.schedule_send);
            ImageView close = dialogView.findViewById(R.id.schedule_close);
            Spinner scheduling = dialogView.findViewById(R.id.schedule_channels_spinner);
            RadioGroup sendAs = dialogView.findViewById(R.id.schedule_radio_group);
            TextView date = dialogView.findViewById(R.id.schedule_date);
            TextView time = dialogView.findViewById(R.id.schedule_time);

            Calendar calendar = Calendar.getInstance();

            date.setText(getDate(Calendar.getInstance().getTime()));
            time.setText(getTime(Calendar.getInstance().getTime()));

            int[] dateSelected = {calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)};
            int[] timeSelected = {calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)};

            date.setOnClickListener(d -> {
                new DatePickerDialog(MessagesActivity.this, (datePicker, year, month, day) -> {
                    date.setText(day + "-" + (month + 1) + "-" + year);
                    dateSelected[0] = day;
                    dateSelected[1] = month + 1;
                    dateSelected[2] = year;
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            });
            time.setOnClickListener(d -> {
                new TimePickerDialog(MessagesActivity.this, (timePicker, hour, minute) -> {
                    timeSelected[0] = hour;
                    timeSelected[1] = minute;
                    time.setText(hour + ":" + minute);
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            });

            close.setOnClickListener(d -> dialog.dismiss());
            scheduling.setAdapter(new ScheduleArrayAdapter(MessagesActivity.this, R.layout.spinner_list_item, R.id.spinner_tv, SCHEDULES));
            scheduling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selected[0] = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            send.setOnClickListener(d -> {
                if(!msg.getText().toString().trim().isEmpty()){
                    Long ts = (stringToDate(dateSelected[0] + "-" + dateSelected[1] + "-" + dateSelected[2] + " " + timeSelected[0] + "-" + timeSelected[1]).getTime());
                    if(sendAs.getCheckedRadioButtonId() == R.id.schedule_user_radio){
                        if(userChannelInfo.getChannel().getIsMember()){
                            dialog.dismiss();
                            if(selected[0] == 0){
                                Call<SuccessResponse> call = retrofitCall.scheduleMessage(USER_TOKEN,id,msg.getText().toString().trim(),String.valueOf(ts/1000),"true");
                                call.enqueue(new Callback<SuccessResponse>() {
                                    @Override
                                    public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                        if(response.isSuccessful()){
                                            Log.e(TAG,String.valueOf(ts));
                                            Toast.makeText(MessagesActivity.this,"Message Scheduled",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SuccessResponse> call, Throwable t) {

                                    }
                                });
                            }else if(selected[0] == 1){
                                for(int i = 0;i <= 7;i++){
                                    Call<SuccessResponse> call = retrofitCall.scheduleMessage(USER_TOKEN,id,msg.getText().toString().trim(),String.valueOf((ts/1000) + (i*86400)),"true");
                                    int finalI = i;
                                    call.enqueue(new Callback<SuccessResponse>() {
                                        @Override
                                        public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                            if(response.isSuccessful() && finalI == 0){
                                                Toast.makeText(MessagesActivity.this,"Message Scheduled",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<SuccessResponse> call, Throwable t) {
                                        }
                                    });
                                }
                            }else if(selected[0] == 2){
                                for(int i = 0;i <= 7;i++){
                                    Call<SuccessResponse> call = retrofitCall.scheduleMessage(USER_TOKEN,id,msg.getText().toString().trim(),String.valueOf((ts/1000) + (i*86400*7)),"true");
                                    int finalI = i;
                                    call.enqueue(new Callback<SuccessResponse>() {
                                        @Override
                                        public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                            if(response.isSuccessful() && finalI == 0){
                                                Toast.makeText(MessagesActivity.this,"Message Scheduled",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SuccessResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            }else if(selected[0] == 3){
                                for(int i = 0;i <= 7;i++){
                                    Call<SuccessResponse> call = retrofitCall.scheduleMessage(USER_TOKEN,id,msg.getText().toString().trim(),String.valueOf((ts/1000) + (i*86400*30)),"true");
                                    int finalI = i;
                                    call.enqueue(new Callback<SuccessResponse>() {
                                        @Override
                                        public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                            if(response.isSuccessful() && finalI == 0){
                                                Toast.makeText(MessagesActivity.this,"Message Scheduled",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SuccessResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }else{
                            Toast.makeText(this,"You are not a member of this channel",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        if(botChannelInfo.getChannel().getIsMember()){
                            dialog.dismiss();
                            if(selected[0] == 0){
                                Call<SuccessResponse> call = retrofitCall.scheduleMessage(BOT_TOKEN,id,msg.getText().toString().trim(),String.valueOf(ts/1000),"false");
                                call.enqueue(new Callback<SuccessResponse>() {
                                    @Override
                                    public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(MessagesActivity.this,"Message Scheduled",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SuccessResponse> call, Throwable t) {

                                    }
                                });
                            }else if(selected[0] == 1){
                                for(int i = 0;i <= 7;i++){
                                    Call<SuccessResponse> call = retrofitCall.scheduleMessage(BOT_TOKEN,id,msg.getText().toString().trim(),String.valueOf((ts/1000) + (i*86400)),"false");
                                    int finalI = i;
                                    call.enqueue(new Callback<SuccessResponse>() {
                                        @Override
                                        public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                            if(response.isSuccessful() && finalI == 0){
                                                Toast.makeText(MessagesActivity.this,"Message Scheduled",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<SuccessResponse> call, Throwable t) {
                                        }
                                    });
                                }
                            }else if(selected[0] == 2){
                                for(int i = 0;i <= 7;i++){
                                    Call<SuccessResponse> call = retrofitCall.scheduleMessage(BOT_TOKEN,id,msg.getText().toString().trim(),String.valueOf((ts/1000) + (i*86400*7)),"false");
                                    int finalI = i;
                                    call.enqueue(new Callback<SuccessResponse>() {
                                        @Override
                                        public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                            if(response.isSuccessful() && finalI == 0){
                                                Toast.makeText(MessagesActivity.this,"Message Scheduled",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SuccessResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            }else if(selected[0] == 3){
                                for(int i = 0;i <= 7;i++){
                                    Call<SuccessResponse> call = retrofitCall.scheduleMessage(BOT_TOKEN,id,msg.getText().toString().trim(),String.valueOf((ts/1000) + (i*86400*30)),"false");
                                    int finalI = i;
                                    call.enqueue(new Callback<SuccessResponse>() {
                                        @Override
                                        public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                            if(response.isSuccessful() && finalI == 0){
                                                Toast.makeText(MessagesActivity.this,"Message Scheduled",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SuccessResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }else{
                            Toast.makeText(this,"ChatBot is not a member of this channel",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            dialog.show();
        });

        if (type.equals("channel")) {
            title.setText(name);
            sendBotBtn.setVisibility(View.VISIBLE);
            timer.setVisibility(View.VISIBLE);
            loadChannelMessages();
            loadChannelInfo();
        } else if(type.equals("dm")) {
            loadUserInfo();
            loadUserMessages();
        } else{
            title.setText(name);
            sendBotBtn.setVisibility(View.VISIBLE);
            loadChannelMessages();
            loadChannelInfo();
            sendBotBtn.setVisibility(View.GONE);
        }
    }

    private void sendMessage() {
        String msg = typedMessage.getText().toString().trim();
        if (type.equals("channel") && !msg.isEmpty()) {
            if (userChannelInfo.getChannel().getIsMember()) {
                typedMessage.setText("");
                Call<SuccessResponse> call = retrofitCall.sendMessage(ADMIN_TOKEN, id, msg, "true");
                call.enqueue(new Callback<SuccessResponse>() {
                    @Override
                    public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                        if (response.isSuccessful()) {
                            loadChannelMessages();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessResponse> call, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(MessagesActivity.this, "You are not a member of this conversation", Toast.LENGTH_LONG).show();
            }

        } else if (!msg.isEmpty()) {
            typedMessage.setText("");
            Call<SuccessResponse> call = retrofitCall.sendMessage(ADMIN_TOKEN, id, msg, "true");
            call.enqueue(new Callback<SuccessResponse>() {
                @Override
                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                    if (response.isSuccessful()) {
                        loadUserMessages();
                    }
                }

                @Override
                public void onFailure(Call<SuccessResponse> call, Throwable t) {

                }
            });
        }
    }

    private void sendAsBot() {
        String msg = typedMessage.getText().toString().trim();
        if (type.equals("channel") && !msg.isEmpty()) {
            if (botChannelInfo.getChannel().getIsMember()) {
                typedMessage.setText("");
                Call<SuccessResponse> call = retrofitCall.sendMessage(BOT_TOKEN, id, msg, "true");
                call.enqueue(new Callback<SuccessResponse>() {
                    @Override
                    public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                        if (response.isSuccessful()) {
                            loadChannelMessages();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessResponse> call, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(MessagesActivity.this, "ChatBot is not a member of this conversation", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadUserInfo() {
        Call<UserProfileAPI> call = retrofitCall.userProfile(BOT_TOKEN, user);
        call.enqueue(new Callback<UserProfileAPI>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UserProfileAPI> call, Response<UserProfileAPI> response) {
                if (response.isSuccessful()) {
                    title.setText(response.body().getUser().getName());
                } else {
                    title.setText(id);
                    try {
                        Log.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfileAPI> call, Throwable t) {
                title.setText(id);
            }
        });
    }

    private void loadChannelMessages() {
        Call<ChannelMessagesAPI> call = retrofitCall.messagesChannel(USER_TOKEN, id);
        call.enqueue(new Callback<ChannelMessagesAPI>() {
            @Override
            public void onResponse(Call<ChannelMessagesAPI> call, Response<ChannelMessagesAPI> response) {
                if (response.isSuccessful()) {
                    channelMessages = response.body();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MessagesActivity.this);
                    layoutManager.setReverseLayout(true);
                    messagesRV.setLayoutManager(layoutManager);
                    messagesRV.setAdapter(new ChannelMessagesAdapter(response.body().getMessages(), retrofitCall));
                    messagesRV.scrollToPosition(0);
                    messagesRV.addOnItemTouchListener(new RecyclerItemClickListener(MessagesActivity.this, messagesRV, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                    .setTitle("Alert !!!")
                                    .setMessage("This action will permanently delete this message from conversation. Are you sure you want to delete this message ?")
                                    .addButton("DELETE", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                        Call<DelMessageAPI> call = retrofitCall.delMessage(USER_TOKEN, id, channelMessages.getMessages().get(position).getTs());
                                        call.enqueue(new Callback<DelMessageAPI>() {
                                            @Override
                                            public void onResponse(Call<DelMessageAPI> call, Response<DelMessageAPI> response) {
                                                if (response.isSuccessful()) {
                                                    loadUserMessages();
                                                } else {
                                                    Toast.makeText(MessagesActivity.this, "Cannot delete this message", Toast.LENGTH_LONG).show();
                                                    try {
                                                        Log.e(TAG, response.errorBody().string());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<DelMessageAPI> call, Throwable t) {
                                                Toast.makeText(MessagesActivity.this, "Cannot delete this message", Toast.LENGTH_LONG).show();
                                                Log.e(TAG, t.getMessage());
                                            }
                                        });
                                        dialog.dismiss();
                                    })
                                    .addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                        dialog.dismiss();
                                    });
                            builder.show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
                } else {
                    try {
                        Log.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChannelMessagesAPI> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void loadUserMessages() {
        Call<DMMessagesAPI> call = retrofitCall.messagesUser(USER_TOKEN, id);
        call.enqueue(new Callback<DMMessagesAPI>() {
            @Override
            public void onResponse(Call<DMMessagesAPI> call, Response<DMMessagesAPI> response) {
                if (response.isSuccessful()) {
                    userMessages = response.body();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MessagesActivity.this);
                    layoutManager.setReverseLayout(true);
                    messagesRV.setLayoutManager(layoutManager);
                    messagesRV.setAdapter(new DMMessagesAdapter(response.body().getMessages(), retrofitCall));
                    messagesRV.scrollToPosition(0);
                    messagesRV.addOnItemTouchListener(new RecyclerItemClickListener(MessagesActivity.this, messagesRV, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                    .setTitle("Alert !!!")
                                    .setMessage("This action will permanently delete this message from conversation. Are you sure you want to delete this message ?")
                                    .addButton("DELETE", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                        Call<DelMessageAPI> call = retrofitCall.delMessage(USER_TOKEN, id, userMessages.getMessages().get(position).getTs());
                                        call.enqueue(new Callback<DelMessageAPI>() {
                                            @Override
                                            public void onResponse(Call<DelMessageAPI> call, Response<DelMessageAPI> response) {
                                                if (response.isSuccessful()) {
                                                    loadUserMessages();
                                                } else {
                                                    Toast.makeText(MessagesActivity.this, "Cannot delete this message", Toast.LENGTH_LONG).show();
                                                    try {
                                                        Log.e(TAG, response.errorBody().string());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<DelMessageAPI> call, Throwable t) {
                                                Toast.makeText(MessagesActivity.this, "Cannot delete this message", Toast.LENGTH_LONG).show();
                                                Log.e(TAG, t.getMessage());
                                            }
                                        });
                                        dialog.dismiss();
                                    })
                                    .addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                        dialog.dismiss();
                                    });
                            builder.show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
                } else {
                    try {
                        Log.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DMMessagesAPI> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void loadChannelInfo() {
        Call<ChannelInfoAPI> callU = retrofitCall.channelInfo(USER_TOKEN, id);
        Call<ChannelInfoAPI> callB = retrofitCall.channelInfo(BOT_TOKEN, id);
        callU.enqueue(new Callback<ChannelInfoAPI>() {
            @Override
            public void onResponse(Call<ChannelInfoAPI> call, Response<ChannelInfoAPI> response) {
                if (response.isSuccessful()) {
                    userChannelInfo = response.body();
                    callB.enqueue(new Callback<ChannelInfoAPI>() {
                        @Override
                        public void onResponse(Call<ChannelInfoAPI> call, Response<ChannelInfoAPI> response) {
                            if (response.isSuccessful()) {
                                botChannelInfo = response.body();
                                if(userChannelInfo.getChannel().getIsChannel() && botChannelInfo.getChannel().getIsChannel()){
                                    if (!userChannelInfo.getChannel().getIsMember() && !botChannelInfo.getChannel().getIsMember()) {
                                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                                .setTitle("Alert !!!")
                                                .setMessage("You are not a member of this channel and neither is ChatBot. Do you want to join this channel ?")
                                                .addButton("ADD ONLY ME", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                    joinChannel(USER_TOKEN, id);
                                                    dialog.dismiss();
                                                })
                                                .addButton("ADD CHATBOT", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                    joinChannel(BOT_TOKEN, id);
                                                    dialog.dismiss();
                                                })
                                                .addButton("ADD BOTH", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                    joinChannel(USER_TOKEN, id);
                                                    joinChannel(BOT_TOKEN, id);
                                                    dialog.dismiss();
                                                })
                                                .addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                    holder.setVisibility(View.GONE);
                                                    dialog.dismiss();
                                                });
                                        builder.show();
                                    } else if (!userChannelInfo.getChannel().getIsMember()) {
                                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                                .setTitle("Alert !!!")
                                                .setMessage("You are not a member of this channel. Do you want to join this channel ?")
                                                .addButton("JOIN", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                    joinChannel(USER_TOKEN, id);
                                                    dialog.dismiss();
                                                })
                                                .addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                    holder.setVisibility(View.VISIBLE);
                                                    dialog.dismiss();
                                                });
                                        builder.show();
                                    } else if (!botChannelInfo.getChannel().getIsMember()) {
                                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                                .setTitle("Alert !!!")
                                                .setMessage("ChatBot is not a member of this channel. Do you want to add ChatBot in this channel ?")
                                                .addButton("ADD CHATBOT", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                    joinChannel(BOT_TOKEN, id);
                                                    dialog.dismiss();
                                                })
                                                .addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                    holder.setVisibility(View.VISIBLE);
                                                    dialog.dismiss();
                                                });
                                        builder.show();
                                    } else {
                                        holder.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                try {
                                    Log.e(TAG, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ChannelInfoAPI> call, Throwable t) {
                            Log.e(TAG, t.getMessage());
                        }
                    });
                } else {
                    try {
                        Log.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChannelInfoAPI> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void joinChannel(String token, String channel) {
        Call<ChannelJoinAPI> call = retrofitCall.joinChannel(token, channel);
        call.enqueue(new Callback<ChannelJoinAPI>() {
            @Override
            public void onResponse(Call<ChannelJoinAPI> call, Response<ChannelJoinAPI> response) {
                if (response.isSuccessful()) {
                    loadChannelMessages();
                    loadChannelInfo();
                } else {
                    try {
                        Log.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChannelJoinAPI> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private String getDate(Date date) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return format.format(date);
    }

    private String getTime(Date date) {
        DateFormat format = new SimpleDateFormat("hh-mm aa", Locale.ENGLISH);
        return format.format(date);
    }

    private Date stringToDate(String aDate) {
        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.ENGLISH);
        return simpledateformat.parse(aDate, pos);

    }
}