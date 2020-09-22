
package com.vys.chatbot.Models.ChannelJoinAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topic {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("last_set")
    @Expose
    private Integer lastSet;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getLastSet() {
        return lastSet;
    }

    public void setLastSet(Integer lastSet) {
        this.lastSet = lastSet;
    }

}
