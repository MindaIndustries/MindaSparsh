package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveRequestModel {

    @SerializedName("LVE_REQNO")
    String LVE_REQNO;
    @SerializedName("EMPNO")
    String EMPNO;
    @SerializedName("LVE_FROMDT")
    String LVE_FROMDT;
    @SerializedName("LVE_TODT")
    String LVE_TODT;
    @SerializedName("LVE_NOOFDAYS")
    double LVE_NOOFDAYS;
    @SerializedName("LVE_PAY")
    String LVE_PAY;
    @SerializedName("REASON_DESC")
    String REASON_DESC;
    @SerializedName("LVE_TYPE")
    String LVE_TYPE;
    @SerializedName("LVE_CODE")
    String LVE_CODE;
    @SerializedName("LVE_RECEIVED_DT")
    String LVE_RECEIVED_DT;
    @SerializedName("LVE_STATUS")
    String LVE_STATUS;
    @SerializedName("LVE_STATUS_CODE")
    String LVE_STATUS_CODE;
    @SerializedName("LVE_AUTH")
    String LVE_AUTH;

    public String getLVE_REQNO() {
        return LVE_REQNO;
    }

    public void setLVE_REQNO(String LVE_REQNO) {
        this.LVE_REQNO = LVE_REQNO;
    }

    public String getEMPNO() {
        return EMPNO;
    }

    public void setEMPNO(String EMPNO) {
        this.EMPNO = EMPNO;
    }

    public String getLVE_FROMDT() {
        return LVE_FROMDT;
    }

    public void setLVE_FROMDT(String LVE_FROMDT) {
        this.LVE_FROMDT = LVE_FROMDT;
    }

    public String getLVE_TODT() {
        return LVE_TODT;
    }

    public void setLVE_TODT(String LVE_TODT) {
        this.LVE_TODT = LVE_TODT;
    }

    public double getLVE_NOOFDAYS() {
        return LVE_NOOFDAYS;
    }

    public void setLVE_NOOFDAYS(double LVE_NOOFDAYS) {
        this.LVE_NOOFDAYS = LVE_NOOFDAYS;
    }

    public String getLVE_PAY() {
        return LVE_PAY;
    }

    public void setLVE_PAY(String LVE_PAY) {
        this.LVE_PAY = LVE_PAY;
    }

    public String getREASON_DESC() {
        return REASON_DESC;
    }

    public void setREASON_DESC(String REASON_DESC) {
        this.REASON_DESC = REASON_DESC;
    }

    public String getLVE_TYPE() {
        return LVE_TYPE;
    }

    public void setLVE_TYPE(String LVE_TYPE) {
        this.LVE_TYPE = LVE_TYPE;
    }

    public String getLVE_CODE() {
        return LVE_CODE;
    }

    public void setLVE_CODE(String LVE_CODE) {
        this.LVE_CODE = LVE_CODE;
    }

    public String getLVE_RECEIVED_DT() {
        return LVE_RECEIVED_DT;
    }

    public void setLVE_RECEIVED_DT(String LVE_RECEIVED_DT) {
        this.LVE_RECEIVED_DT = LVE_RECEIVED_DT;
    }

    public String getLVE_STATUS() {
        return LVE_STATUS;
    }

    public void setLVE_STATUS(String LVE_STATUS) {
        this.LVE_STATUS = LVE_STATUS;
    }

    public String getLVE_STATUS_CODE() {
        return LVE_STATUS_CODE;
    }

    public void setLVE_STATUS_CODE(String LVE_STATUS_CODE) {
        this.LVE_STATUS_CODE = LVE_STATUS_CODE;
    }

    public String getLVE_AUTH() {
        return LVE_AUTH;
    }

    public void setLVE_AUTH(String LVE_AUTH) {
        this.LVE_AUTH = LVE_AUTH;
    }
}

