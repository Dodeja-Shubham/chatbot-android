package com.vys.chatbot.Class;

import com.vys.chatbot.Models.ChannelInfoAPI.ChannelInfoAPI;
import com.vys.chatbot.Models.ChannelJoinAPI.ChannelJoinAPI;
import com.vys.chatbot.Models.ChannelMessagesAPI.Message;
import com.vys.chatbot.Models.ChannelsAPI.Channel;
import com.vys.chatbot.Models.ChannelsAPI.ChannelsAPI;
import com.vys.chatbot.Models.ChannelMessagesAPI.ChannelMessagesAPI;
import com.vys.chatbot.Models.DMMessagesAPI.DMMessagesAPI;
import com.vys.chatbot.Models.DelMessageAPI;
import com.vys.chatbot.Models.SuccessResponse;
import com.vys.chatbot.Models.UserProfileAPI.UserProfileAPI;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiRequestClass {

    public static String BASE_URL = "https://slack.com/api/";

    /**user token api*/

    @GET("conversations.list")
    Call<ChannelsAPI> channels(@Query("token") String token, @QueryMap Map<String, String> query);

    @GET("conversations.history")
    Call<ChannelMessagesAPI> messagesChannel(@Query("token") String token, @Query("channel") String id);

    @GET("conversations.history")
    Call<DMMessagesAPI> messagesUser(@Query("token") String token, @Query("channel") String id);





    /**bot tokens api*/
    @GET("users.info")
    Call<UserProfileAPI> userProfile(@Query("token") String token, @Query("user") String id);







    /**common*/
    @GET("conversations.info")
    Call<ChannelInfoAPI> channelInfo(@Query("token") String token, @Query("channel") String id);

    @POST("conversations.join")
    Call<ChannelJoinAPI> joinChannel(@Query("token") String token, @Query("channel") String id);

    @POST("chat.postMessage")
    Call<SuccessResponse> sendMessage(@Query("token") String token, @Query("channel") String id, @Query("text") String text);

    @POST("chat.delete")
    Call<DelMessageAPI> delMessage(@Query("token") String token, @Query("channel") String id, @Query("ts") String ts);

    @POST("chat.scheduleMessage")
    Call<SuccessResponse> scheduleMessage(@Query("token") String token, @Query("channel") String id, @Query("text") String text, @Query("post_at") String ts);
}
