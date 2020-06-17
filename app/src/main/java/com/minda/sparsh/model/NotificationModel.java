package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 2/24/2018.
 */

public class NotificationModel {
    @SerializedName("PushNotcID")
    @Expose
    private Integer pushNotcID;
    @SerializedName("EmpCode")
    @Expose
    private String empCode;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Notification")
    @Expose
    private String notification;
    @SerializedName("IsRead")
    @Expose
    private Boolean isRead;
    @SerializedName("SentBy")
    @Expose
    private Integer sentBy;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;

    public Integer getPushNotcID() {
        return pushNotcID;
    }

    public void setPushNotcID(Integer pushNotcID) {
        this.pushNotcID = pushNotcID;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getSentBy() {
        return sentBy;
    }

    public void setSentBy(Integer sentBy) {
        this.sentBy = sentBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
