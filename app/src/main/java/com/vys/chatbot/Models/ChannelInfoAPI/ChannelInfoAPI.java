package com.vys.chatbot.Models.ChannelInfoAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vys.chatbot.Models.ChannelsAPI.Channel;

public class ChannelInfoAPI {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("channel")
    @Expose
    private Channel channel;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
