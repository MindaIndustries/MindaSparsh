package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class CityResponse {
     @SerializedName("id")
     String id;
     @SerializedName("District")
     String District;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }
}
