package com.vys.chatbot.Class;

import com.vys.chatbot.Models.ChannelsAPI.ChannelsAPI;
import com.vys.chatbot.Models.MessagesAPI.MessagesAPI;
import com.vys.chatbot.Models.UserProfileAPI.UserProfileAPI;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiRequestClass {

    public static String BASE_URL = "https://slack.com/api/";

    /**user token api*/

    @GET("conversations.list")
    Call<ChannelsAPI> channels(@Query("token") String token, @QueryMap Map<String, String> query);

    @GET("conversations.history")
    Call<MessagesAPI> messages(@Query("token") String token, @QueryMap Map<String, String> query);





    /**bot tokens api*/
    @GET("users.info")
    Call<UserProfileAPI> userProfile(@Query("token") String token, @Query("user") String id);
}
