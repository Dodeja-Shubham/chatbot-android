
package com.vys.chatbot.Models.DMMessagesAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DMMessagesAPI {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    @SerializedName("pin_count")
    @Expose
    private Integer pinCount;
    @SerializedName("channel_actions_ts")
    @Expose
    private Object channelActionsTs;
    @SerializedName("channel_actions_count")
    @Expose
    private Integer channelActionsCount;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getPinCount() {
        return pinCount;
    }

    public void setPinCount(Integer pinCount) {
        this.pinCount = pinCount;
    }

    public Object getChannelActionsTs() {
        return channelActionsTs;
    }

    public void setChannelActionsTs(Object channelActionsTs) {
        this.channelActionsTs = channelActionsTs;
    }

    public Integer getChannelActionsCount() {
        return channelActionsCount;
    }

    public void setChannelActionsCount(Integer channelActionsCount) {
        this.channelActionsCount = channelActionsCount;
    }

}
