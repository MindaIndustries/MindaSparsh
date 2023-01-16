package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class ApplyLeaveResponse {
    @SerializedName("LeaveRequest No")
    String LeaveRequestNo;
    @SerializedName("Msg")
    String Msg;

    public String getLeaveRequestNo() {
        return LeaveRequestNo;
    }

    public void setLeaveRequestNo(String leaveRequestNo) {
        LeaveRequestNo = leaveRequestNo;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
