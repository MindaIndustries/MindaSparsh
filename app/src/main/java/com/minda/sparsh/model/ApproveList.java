package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApproveList {

    @SerializedName("ApprovalId")
    @Expose
    private Integer approvalId;
    @SerializedName("ApprovalStatus")
    @Expose
    private String approvalStatus;
    @SerializedName("ApprovalLevel")
    @Expose
    private String approvalLevel;
    @SerializedName("Action")
    @Expose
    private Object action;
    @SerializedName("Remark")
    @Expose
    private Object remark;
    @SerializedName("RequestId")
    @Expose
    private Integer requestId;
    @SerializedName("AccessRequestNo")
    @Expose
    private String accessRequestNo;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("Flag")
    @Expose
    private String flag;
    @SerializedName("AccessForName")
    @Expose
    private String accessForName;
    @SerializedName("RequestType")
    @Expose
    private String requestType;
    @SerializedName("AccessType")
    @Expose
    private String accessType;
    @SerializedName("AccessSubType")
    @Expose
    private String accessSubType;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("SubCategory")
    @Expose
    private Object subCategory;
    @SerializedName("AccessFor")
    @Expose
    private String accessFor;
    @SerializedName("PlantCode")
    @Expose
    private String plantCode;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("RequestTypeId")
    @Expose
    private Integer requestTypeId;
    @SerializedName("ExternalName")
    @Expose
    private String externalName;
    @SerializedName("CategoryId")
    Integer CategoryId;
    @SerializedName("AccessTypeId")
    Integer AccessTypeId;

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalLevel() {
        return approvalLevel;
    }

    public void setApprovalLevel(String approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getAccessRequestNo() {
        return accessRequestNo;
    }

    public void setAccessRequestNo(String accessRequestNo) {
        this.accessRequestNo = accessRequestNo;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAccessForName() {
        return accessForName;
    }

    public void setAccessForName(String accessForName) {
        this.accessForName = accessForName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAccessSubType() {
        return accessSubType;
    }

    public void setAccessSubType(String accessSubType) {
        this.accessSubType = accessSubType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Object subCategory) {
        this.subCategory = subCategory;
    }

    public String getAccessFor() {
        return accessFor;
    }

    public void setAccessFor(String accessFor) {
        this.accessFor = accessFor;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getRequestTypeId() {
        return requestTypeId;
    }

    public void setRequestTypeId(Integer requestTypeId) {
        this.requestTypeId = requestTypeId;
    }

    public String getExternalName() {
        return externalName;
    }

    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public Integer getAccessTypeId() {
        return AccessTypeId;
    }

    public void setAccessTypeId(Integer accessTypeId) {
        AccessTypeId = accessTypeId;
    }
}
