
package com.vys.chatbot.Models.MessagesAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BotProfile {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("updated")
    @Expose
    private Integer updated;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("icons")
    @Expose
    private Icons icons;
    @SerializedName("team_id")
    @Expose
    private String teamId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Icons getIcons() {
        return icons;
    }

    public void setIcons(Icons icons) {
        this.icons = icons;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

}
