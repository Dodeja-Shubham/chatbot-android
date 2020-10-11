package com.vys.chatbot.Models.SchedulesMessagesAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduledMessage {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("channel_id")
    @Expose
    private String channelId;
    @SerializedName("post_at")
    @Expose
    private Integer postAt;
    @SerializedName("date_created")
    @Expose
    private Integer dateCreated;
    @SerializedName("text")
    @Expose
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getPostAt() {
        return postAt;
    }

    public void setPostAt(Integer postAt) {
        this.postAt = postAt;
    }

    public Integer getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Integer dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
