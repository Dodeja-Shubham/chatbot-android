package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.vys.chatbot.Adapter.ChannelsAdapter;
import com.vys.chatbot.Adapter.EmptyDataShimmerAdapter;
import com.vys.chatbot.Adapter.DMAdapter;
import com.vys.chatbot.Adapter.ScheduledMessagesAdapter;
import com.vys.chatbot.Class.ApiRequestClass;
import com.vys.chatbot.Class.RecyclerItemClickListener;
import com.vys.chatbot.Models.ChannelsAPI.ChannelsAPI;
import com.vys.chatbot.Models.SchedulesMessagesAPI.ScheduledMessagesAPI;
import com.vys.chatbot.Models.SuccessResponse;
import com.vys.chatbot.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    public static String BOT_TOKEN = "";
    public static String USER_TOKEN = "";
    public static Map<String,String> usersNames = new HashMap<>();

    RecyclerView channelsRecyclerView, messagesRecyclerView, scheduledUser, scheduledBot;
    ChannelsAPI channelsData;
    ChannelsAPI messagesData;
    ScheduledMessagesAPI userScheduled,botScheduled;

    MaterialRippleLayout addChannel;

    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiRequestClass.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
    private ApiRequestClass retrofitCall = retrofit.create(ApiRequestClass.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        BOT_TOKEN = getString(R.string.bot_token);
        USER_TOKEN = getString(R.string.user_token);
        try {
            toolbar.setTitle("ChatBot");
            toolbar.setTitleTextColor(getColor(android.R.color.white));
            setSupportActionBar(toolbar);
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }

        channelsRecyclerView = findViewById(R.id.main_channels_rv);
        messagesRecyclerView = findViewById(R.id.main_dm_rv);
        addChannel = findViewById(R.id.main_channel_add);
        scheduledUser = findViewById(R.id.main_sch_user_rv);
        scheduledBot = findViewById(R.id.main_sch_bot_rv);

        channelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduledUser.setLayoutManager(new LinearLayoutManager(this));
        scheduledBot.setLayoutManager(new LinearLayoutManager(this));


        addChannel.setOnClickListener(it -> {
            Dialog dialog = new Dialog(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.add_channel_dialog, null);
            dialog.setContentView(dialogView);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            assert window != null;
            window.setGravity(Gravity.CENTER);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            EditText channelName = dialogView.findViewById(R.id.add_cahnnel_name_et);
            SwitchCompat isPrivate = dialogView.findViewById(R.id.add_channel_channel_type_switch);
            ImageView close = dialogView.findViewById(R.id.add_channel_close);
            Button save = dialogView.findViewById(R.id.add_channel_btn);
            close.setOnClickListener(d -> dialog.dismiss());
            save.setOnClickListener(d -> {
                if(!channelName.getText().toString().isEmpty()){
                    dialog.dismiss();
                    Call<SuccessResponse> call = retrofitCall.createChannel(USER_TOKEN,channelName.getText().toString(),isPrivate.isChecked() ? "true" : "false");
                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                            if(response.isSuccessful()){
                                loadChannelsData();
                            }
                        }

                        @Override
                        public void onFailure(Call<SuccessResponse> call, Throwable t) {

                        }
                    });
                }
            });

            dialog.show();
        });


        loadChannelsData();
        loadMessagesData();
        loadScheduledUser();
        loadScheduledBot();
    }


    private void loadChannelsData() {
        Map<String, String> data = new HashMap<>();
        data.put("types", "public_channel,private_channel");
        channelsRecyclerView.setAdapter(new EmptyDataShimmerAdapter());
        Call<ChannelsAPI> call = retrofitCall.channels(USER_TOKEN, data);
        call.enqueue(new Callback<ChannelsAPI>() {
            @Override
            public void onResponse(Call<ChannelsAPI> call, Response<ChannelsAPI> response) {
                if (response.isSuccessful()) {
                    channelsData = response.body();
                    channelsRecyclerView.setAdapter(new ChannelsAdapter(channelsData.getChannels(),MainActivity.this));
                    channelsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, channelsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
                            intent.putExtra("type", channelsData.getChannels().get(position).getIsChannel() ? "channel" : "group");
                            intent.putExtra("id", channelsData.getChannels().get(position).getId());
                            intent.putExtra("name", channelsData.getChannels().get(position).getName());
                            intent.putExtra("user", "");
                            startActivity(intent);
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
            public void onFailure(Call<ChannelsAPI> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void loadMessagesData() {
        messagesRecyclerView.setAdapter(new EmptyDataShimmerAdapter());
        Map<String, String> data = new HashMap<>();
        data.put("types", "mpim,im");
        Call<ChannelsAPI> call = retrofitCall.channels(USER_TOKEN, data);
        call.enqueue(new Callback<ChannelsAPI>() {
            @Override
            public void onResponse(Call<ChannelsAPI> call, Response<ChannelsAPI> response) {
                if (response.isSuccessful()) {
                    messagesData = response.body();
                    messagesRecyclerView.setAdapter(new DMAdapter(messagesData.getChannels(),MainActivity.this,retrofitCall));
                    messagesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, messagesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
                            intent.putExtra("type","dm");
                            intent.putExtra("id",messagesData.getChannels().get(position).getId());
                            intent.putExtra("name", "");
                            intent.putExtra("user", messagesData.getChannels().get(position).getUser());
                            startActivity(intent);
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
            public void onFailure(Call<ChannelsAPI> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void loadScheduledUser(){
        scheduledUser.setAdapter(new EmptyDataShimmerAdapter());
        Call<ScheduledMessagesAPI> call = retrofitCall.scheduledMessages(USER_TOKEN);
        call.enqueue(new Callback<ScheduledMessagesAPI>() {
            @Override
            public void onResponse(Call<ScheduledMessagesAPI> call, Response<ScheduledMessagesAPI> response) {
                if(response.isSuccessful()){
                    try{
                        userScheduled = response.body();
                        scheduledUser.setAdapter(new ScheduledMessagesAdapter(MainActivity.this,userScheduled.getScheduledMessages()));
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ScheduledMessagesAPI> call, Throwable t) {

            }
        });
    }

    private void loadScheduledBot(){
        scheduledBot.setAdapter(new EmptyDataShimmerAdapter());
        Call<ScheduledMessagesAPI> call = retrofitCall.scheduledMessages(BOT_TOKEN);
        call.enqueue(new Callback<ScheduledMessagesAPI>() {
            @Override
            public void onResponse(Call<ScheduledMessagesAPI> call, Response<ScheduledMessagesAPI> response) {
                if(response.isSuccessful()){
                    try{
                        botScheduled = response.body();
                        scheduledBot.setAdapter(new ScheduledMessagesAdapter(MainActivity.this,botScheduled.getScheduledMessages()));
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ScheduledMessagesAPI> call, Throwable t) {

            }
        });
    }
}