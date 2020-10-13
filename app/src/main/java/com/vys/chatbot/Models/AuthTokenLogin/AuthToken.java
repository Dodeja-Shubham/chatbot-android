
package com.vys.chatbot.Models.AuthTokenLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthToken {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("authed_user")
    @Expose
    private AuthedUser authedUser;
    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("enterprise")
    @Expose
    private Object enterprise;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public AuthedUser getAuthedUser() {
        return authedUser;
    }

    public void setAuthedUser(AuthedUser authedUser) {
        this.authedUser = authedUser;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Object getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Object enterprise) {
        this.enterprise = enterprise;
    }

}
