package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IAMGetAccessSubTypeModel {

    @SerializedName("AccessSubTypeId")
    @Expose
    private Integer accessSubTypeId;
    @SerializedName("AccessTypeId")
    @Expose
    private Integer accessTypeId;
    @SerializedName("AccessSubType")
    @Expose
    private String accessSubType;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("UpdatedOn")
    @Expose
    private Object updatedOn;
    @SerializedName("UpdatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("Status")
    @Expose
    private Boolean status;

    public Integer getAccessSubTypeId() {
        return accessSubTypeId;
    }

    public void setAccessSubTypeId(Integer accessSubTypeId) {
        this.accessSubTypeId = accessSubTypeId;
    }

    public Integer getAccessTypeId() {
        return accessTypeId;
    }

    public void setAccessTypeId(Integer accessTypeId) {
        this.accessTypeId = accessTypeId;
    }

    public String getAccessSubType() {
        return accessSubType;
    }

    public void setAccessSubType(String accessSubType) {
        this.accessSubType = accessSubType;
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

    public Object getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Object updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
