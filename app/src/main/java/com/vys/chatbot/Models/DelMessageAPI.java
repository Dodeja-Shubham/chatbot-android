package com.vys.chatbot.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DelMessageAPI {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("ts")
    @Expose
    private String ts;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

}
