package com.vys.chatbot.Models.SchedulesMessagesAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduledMessagesAPI {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("scheduled_messages")
    @Expose
    private List<ScheduledMessage> scheduledMessages = null;
    @SerializedName("response_metadata")
    @Expose
    private ResponseMetadata responseMetadata;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<ScheduledMessage> getScheduledMessages() {
        return scheduledMessages;
    }

    public void setScheduledMessages(List<ScheduledMessage> scheduledMessages) {
        this.scheduledMessages = scheduledMessages;
    }

    public ResponseMetadata getResponseMetadata() {
        return responseMetadata;
    }

    public void setResponseMetadata(ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
    }

}
