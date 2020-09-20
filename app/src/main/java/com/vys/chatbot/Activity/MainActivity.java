package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.vys.chatbot.Adapter.ChannelsAdapter;
import com.vys.chatbot.Adapter.EmptyDataShimmerAdapter;
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

    RecyclerView channelsRecyclerView;
    ChannelsAPI channelsData;

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

        channelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        channelsRecyclerView.setAdapter(new EmptyDataShimmerAdapter());

        loadChannelsData();
    }


    private void loadChannelsData(){
        Map<String,String> query = new HashMap<>();
        query.put("token",getString(R.string.slack_user_token));
        query.put("type","public_channel,private_channel");
        Call<ChannelsAPI> call = retrofitCall.channels(query);
        call.enqueue(new Callback<ChannelsAPI>() {
            @Override
            public void onResponse(Call<ChannelsAPI> call, Response<ChannelsAPI> response) {
                if(response.isSuccessful()){
                    channelsData = response.body();
                    channelsRecyclerView.setAdapter(new ChannelsAdapter(channelsData.getChannels()));
                }else{
                    try {
                        Log.e(TAG,response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChannelsAPI> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }
}