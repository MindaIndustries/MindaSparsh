package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class EHSObservationModel {
    @SerializedName("id")
    String id;
    @SerializedName("ShortName")
    String ShortName;
    @SerializedName("Name")
    String Name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
