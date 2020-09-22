package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.vys.chatbot.Adapter.ChannelMessagesAdapter;
import com.vys.chatbot.Adapter.DMMessagesAdapter;
import com.vys.chatbot.Class.ApiRequestClass;
import com.vys.chatbot.Models.ChannelInfoAPI.ChannelInfoAPI;
import com.vys.chatbot.Models.ChannelJoinAPI.ChannelJoinAPI;
import com.vys.chatbot.Models.ChannelMessagesAPI.ChannelMessagesAPI;
import com.vys.chatbot.Models.ChannelMessagesAPI.Message;
import com.vys.chatbot.Models.ChannelsAPI.Channel;
import com.vys.chatbot.Models.ChannelsAPI.ChannelsAPI;
import com.vys.chatbot.Models.DMMessagesAPI.DMMessagesAPI;
import com.vys.chatbot.Models.UserProfileAPI.UserProfileAPI;
import com.vys.chatbot.R;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vys.chatbot.Activity.MainActivity.BOT_TOKEN;
import static com.vys.chatbot.Activity.MainActivity.USER_TOKEN;

public class MessagesActivity extends AppCompatActivity {

    private final String TAG = "MessagesActivity";

    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiRequestClass.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
    private ApiRequestClass retrofitCall = retrofit.create(ApiRequestClass.class);

    DMMessagesAPI userMessages;
    ChannelMessagesAPI channelMessages;

    RecyclerView messagesRV;
    MaterialRippleLayout backBtn;
    TextView title;

    ImageView sendBtn;
    EditText typedMessage;

    LinearLayout holder;

    String type = "",id = "",name = "",user = "";

    ChannelInfoAPI userChannelInfo,botChannelInfo;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Bundle extras = getIntent().getExtras();
        try{
            type = extras.getString("type","");
            id = extras.getString("id","");
            name = extras.getString("name", "");
            user = extras.getString("user","");
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }

        if(type.equals("") || id.equals("")){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }

        messagesRV = findViewById(R.id.messages_rv);
        backBtn = findViewById(R.id.messages_back);
        title = findViewById(R.id.messages_title);
        typedMessage = findViewById(R.id.messages_edit_text);
        sendBtn = findViewById(R.id.messages_send_btn);
        holder = findViewById(R.id.send_message_holder);

        holder.setVisibility(View.GONE);

        sendBtn.setOnClickListener(it -> sendMessage());

        backBtn.setOnClickListener(it -> super.onBackPressed());

        if(type.equals("channel")){
            title.setText(name);
            loadChannelMessages();
            loadChannelInfo();
        }else{
            loadUserInfo();
            loadUserMessages();
        }
    }


    private void sendMessage(){
        String msg = typedMessage.getText().toString().trim();
        if(type.equals("channel") && !msg.isEmpty()){

        }else if(!msg.isEmpty()){

        }
    }

    private void loadUserInfo(){
        Call<UserProfileAPI> call = retrofitCall.userProfile(BOT_TOKEN,user);
        call.enqueue(new Callback<UserProfileAPI>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UserProfileAPI> call, Response<UserProfileAPI> response) {
                if(response.isSuccessful()){
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

    private void loadChannelMessages(){
        Call<ChannelMessagesAPI> call = retrofitCall.messagesChannel(USER_TOKEN,id);
        call.enqueue(new Callback<ChannelMessagesAPI>() {
            @Override
            public void onResponse(Call<ChannelMessagesAPI> call, Response<ChannelMessagesAPI> response) {
                if(response.isSuccessful()){
                    channelMessages = response.body();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MessagesActivity.this);
                    layoutManager.setReverseLayout(true);
                    messagesRV.setLayoutManager(layoutManager);
                    messagesRV.setAdapter(new ChannelMessagesAdapter(response.body().getMessages(),retrofitCall));
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

            }
        });
    }

    private void loadUserMessages(){
        Call<DMMessagesAPI> call = retrofitCall.messagesUser(USER_TOKEN,id);
        call.enqueue(new Callback<DMMessagesAPI>() {
            @Override
            public void onResponse(Call<DMMessagesAPI> call, Response<DMMessagesAPI> response) {
                if(response.isSuccessful()){
                    userMessages = response.body();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MessagesActivity.this);
                    layoutManager.setReverseLayout(true);
                    messagesRV.setLayoutManager(layoutManager);
                    messagesRV.setAdapter(new DMMessagesAdapter(response.body().getMessages(),retrofitCall));
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

            }
        });
    }

    private void loadChannelInfo(){
        Call<ChannelInfoAPI> callU = retrofitCall.channelInfoUser(USER_TOKEN,id);
        Call<ChannelInfoAPI> callB = retrofitCall.channelInfoUser(BOT_TOKEN,id);

        callU.enqueue(new Callback<ChannelInfoAPI>() {
            @Override
            public void onResponse(Call<ChannelInfoAPI> call, Response<ChannelInfoAPI> response) {
                if(response.isSuccessful()){
                    userChannelInfo = response.body();
                    callB.enqueue(new Callback<ChannelInfoAPI>() {
                        @Override
                        public void onResponse(Call<ChannelInfoAPI> call, Response<ChannelInfoAPI> response) {
                            if(response.isSuccessful()){
                                botChannelInfo = response.body();
                                if(!userChannelInfo.getChannel().getIsMember() && !botChannelInfo.getChannel().getIsMember()){
                                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                            .setTitle("Alert !!!")
                                            .setMessage("You are not a member of this channel and neither is ChatBot. Do you want to join this channel ?")
                                            .addButton("ADD ONLY ME", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                joinChannel(USER_TOKEN,id);
                                                dialog.dismiss();
                                            })
                                            .addButton("ADD CHATBOT", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                joinChannel(BOT_TOKEN,id);
                                                dialog.dismiss();
                                            })
                                            .addButton("ADD BOTH", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                joinChannel(USER_TOKEN,id);
                                                joinChannel(BOT_TOKEN,id);
                                                dialog.dismiss();
                                            })
                                            .addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                dialog.dismiss();
                                            });
                                    builder.show();
                                }
                                else if(!userChannelInfo.getChannel().getIsMember()){
                                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                            .setTitle("Alert !!!")
                                            .setMessage("You are not a member of this channel. Do you want to join this channel ?")
                                            .addButton("JOIN", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                joinChannel(USER_TOKEN,id);
                                                dialog.dismiss();
                                            })
                                            .addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                holder.setVisibility(View.VISIBLE);
                                                dialog.dismiss();
                                            });
                                    builder.show();
                                }
                                else if(!botChannelInfo.getChannel().getIsMember()){
                                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MessagesActivity.this)
                                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                            .setTitle("Alert !!!")
                                            .setMessage("ChatBot is not a member of this channel. Do you want to add ChatBot in this channel ?")
                                            .addButton("ADD CHATBOT", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                joinChannel(BOT_TOKEN,id);
                                                dialog.dismiss();
                                            })
                                            .addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                                holder.setVisibility(View.VISIBLE);
                                                dialog.dismiss();
                                            });
                                    builder.show();
                                }
                                else {
                                    holder.setVisibility(View.VISIBLE);
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
                            Log.e(TAG,t.getMessage());
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
                Log.e(TAG,t.getMessage());
            }
        });
    }


    private void joinChannel(String token,String channel){
        Call<ChannelJoinAPI> call = retrofitCall.joinChannel(token,channel);
        call.enqueue(new Callback<ChannelJoinAPI>() {
            @Override
            public void onResponse(Call<ChannelJoinAPI> call, Response<ChannelJoinAPI> response) {
                if(response.isSuccessful()){
                    loadChannelMessages();
                    loadChannelInfo();
                }else{
                    try {
                        Log.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChannelJoinAPI> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

}