
package com.vys.chatbot.Models.MessagesAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("bot_id")
    @Expose
    private String botId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("ts")
    @Expose
    private String ts;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("bot_profile")
    @Expose
    private BotProfile botProfile;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("inviter")
    @Expose
    private String inviter;

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public BotProfile getBotProfile() {
        return botProfile;
    }

    public void setBotProfile(BotProfile botProfile) {
        this.botProfile = botProfile;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

}
