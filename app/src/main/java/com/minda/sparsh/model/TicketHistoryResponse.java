package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class TicketHistoryResponse {
    @SerializedName("TicketNo")
    String TicketNo;
    @SerializedName("EName")
    String EName;
    @SerializedName("Remark")
    String Remark;
    @SerializedName("RemarkFor")
    String RemarkFor;
    @SerializedName("CreatedOn")
    String CreatedOn;

    public String getTicketNo() {
        return TicketNo;
    }

    public void setTicketNo(String ticketNo) {
        TicketNo = ticketNo;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getRemarkFor() {
        return RemarkFor;
    }

    public void setRemarkFor(String remarkFor) {
        RemarkFor = remarkFor;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
