package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class SaveCalendarResponse {
    @SerializedName("Data")
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
