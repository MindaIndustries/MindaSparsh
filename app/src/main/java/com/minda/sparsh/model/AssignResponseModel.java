package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class AssignResponseModel {
    @SerializedName("Message")
    String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
