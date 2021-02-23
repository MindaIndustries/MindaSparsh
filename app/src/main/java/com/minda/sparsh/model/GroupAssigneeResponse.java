package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class GroupAssigneeResponse {
    @SerializedName("Name")
    String Name;
    @SerializedName("Default")
    String Default;
    @SerializedName("Data")
    String Data;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDefault() {
        return Default;
    }

    public void setDefault(String aDefault) {
        Default = aDefault;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
