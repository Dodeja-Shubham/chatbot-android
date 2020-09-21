
package com.vys.chatbot.Models.MessagesAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Icons {

    @SerializedName("image_36")
    @Expose
    private String image36;
    @SerializedName("image_48")
    @Expose
    private String image48;
    @SerializedName("image_72")
    @Expose
    private String image72;

    public String getImage36() {
        return image36;
    }

    public void setImage36(String image36) {
        this.image36 = image36;
    }

    public String getImage48() {
        return image48;
    }

    public void setImage48(String image48) {
        this.image48 = image48;
    }

    public String getImage72() {
        return image72;
    }

    public void setImage72(String image72) {
        this.image72 = image72;
    }

}
