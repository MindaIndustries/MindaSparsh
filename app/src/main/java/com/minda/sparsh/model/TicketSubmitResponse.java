package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class TicketSubmitResponse {
    @SerializedName("Description")
    String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
