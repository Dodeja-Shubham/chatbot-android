package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.vys.chatbot.Class.SlackApiRequestClass;
import com.vys.chatbot.Models.AuthTokenLogin.AuthToken;
import com.vys.chatbot.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.vys.chatbot.Activity.SplashActivity.*;

public class LoginActivity extends AppCompatActivity {

    WebView web;
    LinearLayout loginProgress;

    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(SlackApiRequestClass.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
    private SlackApiRequestClass retrofitCall = retrofit.create(SlackApiRequestClass.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        web = findViewById(R.id.login_web);
        loginProgress = findViewById(R.id.login_progress);
        loginProgress.setVisibility(View.GONE);

        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("URL",request.getUrl().toString());
                if(request.getUrl().toString().startsWith("https://localhost:3000/?error=access_denied")){
                    finish();
                }
                else if(request.getUrl().toString().startsWith("https://localhost:3000/")){
                    web.stopLoading();
                    web.clearCache(true);
                    web.clearHistory();
                    web.setVisibility(View.GONE);
                    String code = request.getUrl().toString().substring(request.getUrl().toString().indexOf("code=") + 5,request.getUrl().toString().indexOf("&state="));
                    Call<AuthToken> call = retrofitCall.getToken(code,getString(R.string.client_id),getString(R.string.client_secret),"https://localhost:3000/");
                    call.enqueue(new Callback<AuthToken>() {
                        @Override
                        public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                            if(response.isSuccessful()){
                                if(response.body() == null || response.body().getAuthedUser() == null || response.body().getAuthedUser().getAccessToken() == null){
                                    web.loadUrl("https://slack.com/oauth/v2/authorize?client_id=1371445904901.1374535588306&user_scope=channels:history,channels:read,channels:write,chat:write,groups:history,groups:read,groups:write,identify,im:write,mpim:history,mpim:read&redirect_uri=https://localhost:3000/");
                                }else{
                                    web.destroy();
                                    getSharedPreferences("DEFAULT",MODE_PRIVATE).edit().putString("USER",response.body().getAuthedUser().getAccessToken()).apply();
                                    getSharedPreferences("DEFAULT",MODE_PRIVATE).edit().putBoolean("logged",true).apply();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthToken> call, Throwable t) {

                        }
                    });
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        web.clearCache(true);
        web.clearHistory();
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 9_3 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13E233 Safari/601.1");
        web.loadUrl("https://slack.com/oauth/v2/authorize?client_id=1371445904901.1374535588306&user_scope=channels:history,channels:read,channels:write,chat:write,groups:history,groups:read,groups:write,identify,im:write,mpim:history,mpim:read&redirect_uri=https://localhost:3000/");
    }
}