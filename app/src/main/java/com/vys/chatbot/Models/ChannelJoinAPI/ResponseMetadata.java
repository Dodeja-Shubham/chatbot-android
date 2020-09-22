
package com.vys.chatbot.Models.ChannelJoinAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMetadata {

    @SerializedName("warnings")
    @Expose
    private List<String> warnings = null;

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

}
