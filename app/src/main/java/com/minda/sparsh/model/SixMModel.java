package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class SixMModel {
    @SerializedName("ID")
    String ID;
    @SerializedName("Name")
    String Name;
    @SerializedName("VAl")
    String VAl;


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

    public String getVAl() {
        return VAl;
    }

    public void setVAl(String VAl) {
        this.VAl = VAl;
    }
}
