package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessRequestDetailsModel {
    @SerializedName("RequestId")
    @Expose
    private Integer requestId;
    @SerializedName("AccessRequestNo")
    @Expose
    private String accessRequestNo;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("RequestTypeId")
    @Expose
    private Integer requestTypeId;
    @SerializedName("AccessSubTypeId")
    @Expose
    private Integer accessSubTypeId;
    @SerializedName("AccessTypeId")
    @Expose
    private Integer accessTypeId;
    @SerializedName("ExternalName")
    @Expose
    private String externalName;
    @SerializedName("AccessForType")
    @Expose
    private Integer accessForType;
    @SerializedName("AccessForSubType")
    @Expose
    private Integer accessForSubType;
    @SerializedName("AccessFor")
    @Expose
    private String accessFor;
    @SerializedName("Organisation")
    @Expose
    private String organisation;
    @SerializedName("Purpose")
    @Expose
    private String purpose;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("SubCategoryId")
    @Expose
    private Integer subCategoryId;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("RequirementDetail")
    @Expose
    private String requirementDetail;
    @SerializedName("Attachment")
    @Expose
    private String attachment;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Flag")
    @Expose
    private String flag;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("Attachment1")
    @Expose
    private Object attachment1;
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
    @SerializedName("ProfileName")
    @Expose
    private Object profileName;
    @SerializedName("AccessForName")
    @Expose
    private String accessForName;
    @SerializedName("CreatedByName")
    @Expose
    private String createdByName;

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

    public Integer getRequestTypeId() {
        return requestTypeId;
    }

    public void setRequestTypeId(Integer requestTypeId) {
        this.requestTypeId = requestTypeId;
    }

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

    public String getExternalName() {
        return externalName;
    }

    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    public Integer getAccessForType() {
        return accessForType;
    }

    public void setAccessForType(Integer accessForType) {
        this.accessForType = accessForType;
    }

    public Integer getAccessForSubType() {
        return accessForSubType;
    }

    public void setAccessForSubType(Integer accessForSubType) {
        this.accessForSubType = accessForSubType;
    }

    public String getAccessFor() {
        return accessFor;
    }

    public void setAccessFor(String accessFor) {
        this.accessFor = accessFor;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getRequirementDetail() {
        return requirementDetail;
    }

    public void setRequirementDetail(String requirementDetail) {
        this.requirementDetail = requirementDetail;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(Object attachment1) {
        this.attachment1 = attachment1;
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

    public Object getProfileName() {
        return profileName;
    }

    public void setProfileName(Object profileName) {
        this.profileName = profileName;
    }

    public String getAccessForName() {
        return accessForName;
    }

    public void setAccessForName(String accessForName) {
        this.accessForName = accessForName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
}
