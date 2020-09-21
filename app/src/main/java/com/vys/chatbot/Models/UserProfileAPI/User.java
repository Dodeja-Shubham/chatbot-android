
package com.vys.chatbot.Models.UserProfileAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("real_name")
    @Expose
    private String realName;
    @SerializedName("tz")
    @Expose
    private String tz;
    @SerializedName("tz_label")
    @Expose
    private String tzLabel;
    @SerializedName("tz_offset")
    @Expose
    private Integer tzOffset;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("is_admin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("is_owner")
    @Expose
    private Boolean isOwner;
    @SerializedName("is_primary_owner")
    @Expose
    private Boolean isPrimaryOwner;
    @SerializedName("is_restricted")
    @Expose
    private Boolean isRestricted;
    @SerializedName("is_ultra_restricted")
    @Expose
    private Boolean isUltraRestricted;
    @SerializedName("is_bot")
    @Expose
    private Boolean isBot;
    @SerializedName("is_app_user")
    @Expose
    private Boolean isAppUser;
    @SerializedName("updated")
    @Expose
    private Integer updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getTzLabel() {
        return tzLabel;
    }

    public void setTzLabel(String tzLabel) {
        this.tzLabel = tzLabel;
    }

    public Integer getTzOffset() {
        return tzOffset;
    }

    public void setTzOffset(Integer tzOffset) {
        this.tzOffset = tzOffset;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    public Boolean getIsPrimaryOwner() {
        return isPrimaryOwner;
    }

    public void setIsPrimaryOwner(Boolean isPrimaryOwner) {
        this.isPrimaryOwner = isPrimaryOwner;
    }

    public Boolean getIsRestricted() {
        return isRestricted;
    }

    public void setIsRestricted(Boolean isRestricted) {
        this.isRestricted = isRestricted;
    }

    public Boolean getIsUltraRestricted() {
        return isUltraRestricted;
    }

    public void setIsUltraRestricted(Boolean isUltraRestricted) {
        this.isUltraRestricted = isUltraRestricted;
    }

    public Boolean getIsBot() {
        return isBot;
    }

    public void setIsBot(Boolean isBot) {
        this.isBot = isBot;
    }

    public Boolean getIsAppUser() {
        return isAppUser;
    }

    public void setIsAppUser(Boolean isAppUser) {
        this.isAppUser = isAppUser;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

}
