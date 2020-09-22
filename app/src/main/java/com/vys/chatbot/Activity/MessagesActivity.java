package com.vys.chatbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.vys.chatbot.Adapter.ChannelMessagesAdapter;
import com.vys.chatbot.Adapter.DMMessagesAdapter;
import com.vys.chatbot.Class.ApiRequestClass;
import com.vys.chatbot.Models.ChannelMessagesAPI.ChannelMessagesAPI;
import com.vys.chatbot.Models.ChannelMessagesAPI.Message;
import com.vys.chatbot.Models.DMMessagesAPI.DMMessagesAPI;
import com.vys.chatbot.Models.UserProfileAPI.UserProfileAPI;
import com.vys.chatbot.R;

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

    String type = "",id = "",name = "",user = "";

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

        sendBtn.setOnClickListener(it -> sendMessage());

        backBtn.setOnClickListener(it -> super.onBackPressed());

        if(type.equals("channel")){
            title.setText(name);
            loadChannelMessages();
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
                }else{
                    title.setText(id);
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
                }
            }

            @Override
            public void onFailure(Call<DMMessagesAPI> call, Throwable t) {

            }
        });
    }

}