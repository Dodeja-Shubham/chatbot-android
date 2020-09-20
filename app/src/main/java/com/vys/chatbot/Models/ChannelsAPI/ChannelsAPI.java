
package com.vys.chatbot.Models.ChannelsAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelsAPI {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("channels")
    @Expose
    private List<Channel> channels = null;
    @SerializedName("response_metadata")
    @Expose
    private ResponseMetadata responseMetadata;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public ResponseMetadata getResponseMetadata() {
        return responseMetadata;
    }

    public void setResponseMetadata(ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
    }

}
