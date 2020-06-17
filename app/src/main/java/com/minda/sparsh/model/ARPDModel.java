package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ARPDModel {
    @SerializedName("ApprovalId")
    @Expose
    private Integer approvalId;
    @SerializedName("ApprovalBy")
    @Expose
    private String approvalBy;
    @SerializedName("ApprovalByName")
    @Expose
    private String approvalByName;
    @SerializedName("ApprovalStatus")
    @Expose
    private String approvalStatus;
    @SerializedName("ApprovalLevel")
    @Expose
    private String approvalLevel;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("Flag")
    @Expose
    private Object flag;
    @SerializedName("Remark")
    @Expose
    private Object remark;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("ProcessorNo")
    @Expose
    private Integer processorNo;
    @SerializedName("UpdatedOn")
    @Expose
    private Object updatedOn;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Attachment")
    @Expose
    private Object attachment;

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy;
    }

    public String getApprovalByName() {
        return approvalByName;
    }

    public void setApprovalByName(String approvalByName) {
        this.approvalByName = approvalByName;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getProcessorNo() {
        return processorNo;
    }

    public void setProcessorNo(Integer processorNo) {
        this.processorNo = processorNo;
    }

    public Object getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Object updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

}
