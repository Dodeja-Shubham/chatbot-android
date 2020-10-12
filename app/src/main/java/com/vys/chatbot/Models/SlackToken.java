package com.vys.chatbot.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SlackToken {

    @SerializedName("logged in user token")
    @Expose
    private String loggedInUserToken;
    @SerializedName("bot token")
    @Expose
    private String botToken;
    @SerializedName("admin token")
    @Expose
    private String adminToken;

    public String getLoggedInUserToken() {
        return loggedInUserToken;
    }

    public void setLoggedInUserToken(String loggedInUserToken) {
        this.loggedInUserToken = loggedInUserToken;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
    }

}
