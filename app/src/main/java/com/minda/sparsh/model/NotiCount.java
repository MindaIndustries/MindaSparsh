package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class NotiCount {
    @SerializedName("Val")
    Integer Val;

    public Integer getVal() {
        return Val;
    }

    public void setVal(Integer val) {
        Val = val;
    }
}
