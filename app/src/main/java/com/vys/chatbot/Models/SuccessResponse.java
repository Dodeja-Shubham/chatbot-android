package com.vys.chatbot.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuccessResponse {
    private String detail;
    public void setDetail(String detail){
        this.detail = detail;
    }
    public String getDetail(){
        return this.detail;
    }
}
