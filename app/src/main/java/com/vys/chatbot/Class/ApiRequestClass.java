package com.vys.chatbot.Class;

import com.vys.chatbot.Models.ChannelsAPI.ChannelsAPI;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiRequestClass {

    public static String BASE_URL = "https://slack.com/api/";

    @GET("conversations.list")
    Call<ChannelsAPI> channels(@QueryMap Map<String,String> query);
}
