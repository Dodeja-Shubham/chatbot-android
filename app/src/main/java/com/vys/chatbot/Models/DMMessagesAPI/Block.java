
package com.vys.chatbot.Models.DMMessagesAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Block {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("block_id")
    @Expose
    private String blockId;
    @SerializedName("elements")
    @Expose
    private List<Element> elements = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

}
