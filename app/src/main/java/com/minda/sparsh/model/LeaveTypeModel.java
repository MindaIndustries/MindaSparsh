package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveTypeModel {

    @SerializedName("Id")
    long Id;
    @SerializedName("lvtyp")
    String lvtyp;
    @SerializedName("short_Desc")
    String short_Desc;


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getLvtyp() {
        return lvtyp;
    }

    public void setLvtyp(String lvtyp) {
        this.lvtyp = lvtyp;
    }

    public String getShort_Desc() {
        return short_Desc;
    }

    public void setShort_Desc(String short_Desc) {
        this.short_Desc = short_Desc;
    }
}
