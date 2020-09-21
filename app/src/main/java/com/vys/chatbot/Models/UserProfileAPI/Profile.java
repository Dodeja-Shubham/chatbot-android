
package com.vys.chatbot.Models.UserProfileAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("skype")
    @Expose
    private String skype;
    @SerializedName("real_name")
    @Expose
    private String realName;
    @SerializedName("real_name_normalized")
    @Expose
    private String realNameNormalized;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("display_name_normalized")
    @Expose
    private String displayNameNormalized;
    @SerializedName("fields")
    @Expose
    private Object fields;
    @SerializedName("status_text")
    @Expose
    private String statusText;
    @SerializedName("status_emoji")
    @Expose
    private String statusEmoji;
    @SerializedName("status_expiration")
    @Expose
    private Integer statusExpiration;
    @SerializedName("avatar_hash")
    @Expose
    private String avatarHash;
    @SerializedName("image_24")
    @Expose
    private String image24;
    @SerializedName("image_32")
    @Expose
    private String image32;
    @SerializedName("image_48")
    @Expose
    private String image48;
    @SerializedName("image_72")
    @Expose
    private String image72;
    @SerializedName("image_192")
    @Expose
    private String image192;
    @SerializedName("image_512")
    @Expose
    private String image512;
    @SerializedName("status_text_canonical")
    @Expose
    private String statusTextCanonical;
    @SerializedName("team")
    @Expose
    private String team;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealNameNormalized() {
        return realNameNormalized;
    }

    public void setRealNameNormalized(String realNameNormalized) {
        this.realNameNormalized = realNameNormalized;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayNameNormalized() {
        return displayNameNormalized;
    }

    public void setDisplayNameNormalized(String displayNameNormalized) {
        this.displayNameNormalized = displayNameNormalized;
    }

    public Object getFields() {
        return fields;
    }

    public void setFields(Object fields) {
        this.fields = fields;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusEmoji() {
        return statusEmoji;
    }

    public void setStatusEmoji(String statusEmoji) {
        this.statusEmoji = statusEmoji;
    }

    public Integer getStatusExpiration() {
        return statusExpiration;
    }

    public void setStatusExpiration(Integer statusExpiration) {
        this.statusExpiration = statusExpiration;
    }

    public String getAvatarHash() {
        return avatarHash;
    }

    public void setAvatarHash(String avatarHash) {
        this.avatarHash = avatarHash;
    }

    public String getImage24() {
        return image24;
    }

    public void setImage24(String image24) {
        this.image24 = image24;
    }

    public String getImage32() {
        return image32;
    }

    public void setImage32(String image32) {
        this.image32 = image32;
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

    public String getImage192() {
        return image192;
    }

    public void setImage192(String image192) {
        this.image192 = image192;
    }

    public String getImage512() {
        return image512;
    }

    public void setImage512(String image512) {
        this.image512 = image512;
    }

    public String getStatusTextCanonical() {
        return statusTextCanonical;
    }

    public void setStatusTextCanonical(String statusTextCanonical) {
        this.statusTextCanonical = statusTextCanonical;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

}
