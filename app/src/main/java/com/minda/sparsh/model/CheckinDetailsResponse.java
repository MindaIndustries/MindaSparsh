package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class CheckinDetailsResponse {

    @SerializedName("Status")
    String Status;
    @SerializedName("OutTime")
    String OutTime;
    @SerializedName("InTime")
    String InTime;
    @SerializedName("RIntime")
    String RIntime;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
    }

    public String getInTime() {
        return InTime;
    }

    public void setInTime(String inTime) {
        InTime = inTime;
    }

    public String getRIntime() {
        return RIntime;
    }

    public void setRIntime(String RIntime) {
        this.RIntime = RIntime;
    }
}
