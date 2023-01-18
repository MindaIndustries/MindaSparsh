package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveDaysResponse {
    @SerializedName("Count")
    double Count;


    public double getCount() {
        return Count;
    }

    public void setCount(double count) {
        Count = count;
    }
}
