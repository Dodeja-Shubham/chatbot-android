package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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

    public static String BOT_TOKEN = "";
    public static String USER_TOKEN = "";
    public static String ADMIN_TOKEN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ADMIN_TOKEN = getString(R.string.user_token);
        BOT_TOKEN = getString(R.string.bot_token);
        if(getSharedPreferences("DEFAULT",MODE_PRIVATE).getBoolean("logged",false)){
            USER_TOKEN = getSharedPreferences("DEFAULT",MODE_PRIVATE).getString("USER","");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }, 1000);
        }else{
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }, 1000);
        }
    }
}