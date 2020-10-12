package com.vys.chatbot.Class;

import com.vys.chatbot.Models.ChannelsAPI.ChannelsAPI;
import com.vys.chatbot.Models.SlackToken;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiRequestClass {

    public static String BASE_URL = "https://chatbotmckinley.herokuapp.com/";

    @GET("slack/token/")
    Call<SlackToken> getToken();
}
