package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class SixMModel {
    @SerializedName("ID")
    String ID;
    @SerializedName("Name")
    String Name;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
