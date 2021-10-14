package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class IAMBModel {
    @SerializedName("Status")
    String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
