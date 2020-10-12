package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.vys.chatbot.Class.ApiRequestClass;
import com.vys.chatbot.Models.SlackToken;
import com.vys.chatbot.R;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiRequestClass.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
    private ApiRequestClass retrofitCall = retrofit.create(ApiRequestClass.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Call<SlackToken> call = retrofitCall.getToken();
        call.enqueue(new Callback<SlackToken>() {
            @Override
            public void onResponse(Call<SlackToken> call, Response<SlackToken> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.putExtra("user",response.body().getAdminToken());
                    intent.putExtra("bot",response.body().getBotToken());
                    intent.putExtra("admin",response.body().getAdminToken());
//                    intent.putExtra("user",getString(R.string.user_token));
//                    intent.putExtra("bot",getString(R.string.bot_token));
//                    intent.putExtra("admin",getString(R.string.user_token));
                    new Handler().postDelayed(() -> {
                        startActivity(intent);
                    },1000);
                } else {
                    Toast.makeText(SplashActivity.this,"Unable to connect to internet",Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SlackToken> call, Throwable t) {
                Toast.makeText(SplashActivity.this,"Unable to connect to internet",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}