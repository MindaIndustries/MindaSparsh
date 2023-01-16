package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveDaysResponse {
    @SerializedName("Count")
    int Count;


    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
