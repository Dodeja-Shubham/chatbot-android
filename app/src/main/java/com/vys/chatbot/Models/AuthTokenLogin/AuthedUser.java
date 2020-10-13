
package com.vys.chatbot.Models.AuthTokenLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthedUser {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

}
