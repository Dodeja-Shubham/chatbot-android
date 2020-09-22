
package com.vys.chatbot.Models.ChannelJoinAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Channel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_channel")
    @Expose
    private Boolean isChannel;
    @SerializedName("is_group")
    @Expose
    private Boolean isGroup;
    @SerializedName("is_im")
    @Expose
    private Boolean isIm;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("is_archived")
    @Expose
    private Boolean isArchived;
    @SerializedName("is_general")
    @Expose
    private Boolean isGeneral;
    @SerializedName("unlinked")
    @Expose
    private Integer unlinked;
    @SerializedName("name_normalized")
    @Expose
    private String nameNormalized;
    @SerializedName("is_shared")
    @Expose
    private Boolean isShared;
    @SerializedName("parent_conversation")
    @Expose
    private Object parentConversation;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("is_ext_shared")
    @Expose
    private Boolean isExtShared;
    @SerializedName("is_org_shared")
    @Expose
    private Boolean isOrgShared;
    @SerializedName("shared_team_ids")
    @Expose
    private List<String> sharedTeamIds = null;
    @SerializedName("pending_shared")
    @Expose
    private List<Object> pendingShared = null;
    @SerializedName("pending_connected_team_ids")
    @Expose
    private List<Object> pendingConnectedTeamIds = null;
    @SerializedName("is_pending_ext_shared")
    @Expose
    private Boolean isPendingExtShared;
    @SerializedName("is_member")
    @Expose
    private Boolean isMember;
    @SerializedName("is_private")
    @Expose
    private Boolean isPrivate;
    @SerializedName("is_mpim")
    @Expose
    private Boolean isMpim;
    @SerializedName("topic")
    @Expose
    private Topic topic;
    @SerializedName("purpose")
    @Expose
    private Purpose purpose;
    @SerializedName("previous_names")
    @Expose
    private List<Object> previousNames = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsChannel() {
        return isChannel;
    }

    public void setIsChannel(Boolean isChannel) {
        this.isChannel = isChannel;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public Boolean getIsIm() {
        return isIm;
    }

    public void setIsIm(Boolean isIm) {
        this.isIm = isIm;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public Boolean getIsGeneral() {
        return isGeneral;
    }

    public void setIsGeneral(Boolean isGeneral) {
        this.isGeneral = isGeneral;
    }

    public Integer getUnlinked() {
        return unlinked;
    }

    public void setUnlinked(Integer unlinked) {
        this.unlinked = unlinked;
    }

    public String getNameNormalized() {
        return nameNormalized;
    }

    public void setNameNormalized(String nameNormalized) {
        this.nameNormalized = nameNormalized;
    }

    public Boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }

    public Object getParentConversation() {
        return parentConversation;
    }

    public void setParentConversation(Object parentConversation) {
        this.parentConversation = parentConversation;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean getIsExtShared() {
        return isExtShared;
    }

    public void setIsExtShared(Boolean isExtShared) {
        this.isExtShared = isExtShared;
    }

    public Boolean getIsOrgShared() {
        return isOrgShared;
    }

    public void setIsOrgShared(Boolean isOrgShared) {
        this.isOrgShared = isOrgShared;
    }

    public List<String> getSharedTeamIds() {
        return sharedTeamIds;
    }

    public void setSharedTeamIds(List<String> sharedTeamIds) {
        this.sharedTeamIds = sharedTeamIds;
    }

    public List<Object> getPendingShared() {
        return pendingShared;
    }

    public void setPendingShared(List<Object> pendingShared) {
        this.pendingShared = pendingShared;
    }

    public List<Object> getPendingConnectedTeamIds() {
        return pendingConnectedTeamIds;
    }

    public void setPendingConnectedTeamIds(List<Object> pendingConnectedTeamIds) {
        this.pendingConnectedTeamIds = pendingConnectedTeamIds;
    }

    public Boolean getIsPendingExtShared() {
        return isPendingExtShared;
    }

    public void setIsPendingExtShared(Boolean isPendingExtShared) {
        this.isPendingExtShared = isPendingExtShared;
    }

    public Boolean getIsMember() {
        return isMember;
    }

    public void setIsMember(Boolean isMember) {
        this.isMember = isMember;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Boolean getIsMpim() {
        return isMpim;
    }

    public void setIsMpim(Boolean isMpim) {
        this.isMpim = isMpim;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public List<Object> getPreviousNames() {
        return previousNames;
    }

    public void setPreviousNames(List<Object> previousNames) {
        this.previousNames = previousNames;
    }

}
