package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveRegularizeModel {
    @SerializedName("leavecode")
    int leavecode;
    @SerializedName("leave")
    String leave;

    public int getLeavecode() {
        return leavecode;
    }

    public void setLeavecode(int leavecode) {
        this.leavecode = leavecode;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }
}
