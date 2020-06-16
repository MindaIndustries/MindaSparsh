package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class EHSIdentifiedLocationModel {
    @SerializedName("Name")
    String Name;
    @SerializedName("ID")
    String ID;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
