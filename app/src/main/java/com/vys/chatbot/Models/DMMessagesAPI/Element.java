
package com.vys.chatbot.Models.DMMessagesAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Element {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("elements")
    @Expose
    private List<Element_> elements = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Element_> getElements() {
        return elements;
    }

    public void setElements(List<Element_> elements) {
        this.elements = elements;
    }

}
