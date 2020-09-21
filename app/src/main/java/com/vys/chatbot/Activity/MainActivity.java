package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.vys.chatbot.Adapter.ChannelsAdapter;
import com.vys.chatbot.Adapter.EmptyDataShimmerAdapter;
import com.vys.chatbot.Adapter.DMAdapter;
import com.vys.chatbot.Class.ApiRequestClass;
import com.vys.chatbot.Models.ChannelsAPI.ChannelsAPI;
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
    public static String BOT_TOKEN = "xoxb-1371445904901-1387166513201-Im5Gl4Cy4JWqqvDqOsgRbXtm";
    public static String USER_TOKEN = "xoxp-1371445904901-1387164227009-1380728937556-34727a5d34583f751d1d17c5169f18f1";

    RecyclerView channelsRecyclerView, messagesRecyclerView;
    ChannelsAPI channelsData;
    ChannelsAPI messagesData;

    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiRequestClass.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
    private ApiRequestClass retrofitCall = retrofit.create(ApiRequestClass.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        channelsRecyclerView = findViewById(R.id.main_channels_rv);
        messagesRecyclerView = findViewById(R.id.main_dm_rv);

        channelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadChannelsData();
        loadMessagesData();
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
}