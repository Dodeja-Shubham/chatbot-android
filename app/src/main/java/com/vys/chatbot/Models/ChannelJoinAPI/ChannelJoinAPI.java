
package com.vys.chatbot.Models.ChannelJoinAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelJoinAPI {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("channel")
    @Expose
    private Channel channel;
    @SerializedName("warning")
    @Expose
    private String warning;
    @SerializedName("response_metadata")
    @Expose
    private ResponseMetadata responseMetadata;

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

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public ResponseMetadata getResponseMetadata() {
        return responseMetadata;
    }

    public void setResponseMetadata(ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
    }

}
