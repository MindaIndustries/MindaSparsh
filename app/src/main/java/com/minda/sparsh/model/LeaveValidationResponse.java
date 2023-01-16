package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveValidationResponse {
    @SerializedName("Data")
    String Data;

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
