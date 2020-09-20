
package com.vys.chatbot.Models.ChannelsAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMetadata {

    @SerializedName("next_cursor")
    @Expose
    private String nextCursor;

    public String getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

}
