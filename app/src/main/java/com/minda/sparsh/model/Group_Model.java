package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 6/7/2018.
 */

public class Group_Model {
    @SerializedName("GID")
    @Expose
    private Integer gID;
    @SerializedName("GroupName")
    @Expose
    private String groupName;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    public Integer getGID() {
        return gID;
    }

    public void setGID(Integer gID) {
        this.gID = gID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
