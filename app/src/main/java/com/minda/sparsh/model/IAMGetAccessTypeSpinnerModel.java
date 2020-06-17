package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IAMGetAccessTypeSpinnerModel {
    @SerializedName("AccessTypeId")
    @Expose
    private Integer accessTypeId;
    @SerializedName("AccessType")
    @Expose
    private String accessType;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("UpdatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("UpdatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("Status")
    @Expose
    private Boolean status;

    public Integer getAccessTypeId() {
        return accessTypeId;
    }

    public void setAccessTypeId(Integer accessTypeId) {
        this.accessTypeId = accessTypeId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
