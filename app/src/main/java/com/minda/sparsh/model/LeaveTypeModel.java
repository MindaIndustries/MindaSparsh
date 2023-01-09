package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveTypeModel {

    @SerializedName("lve_type_code")
    String lve_type_code;
    @SerializedName("lve_type_desc")
    String lve_type_desc;
    @SerializedName("active")
    boolean active;

    public String getLve_type_code() {
        return lve_type_code;
    }

    public void setLve_type_code(String lve_type_code) {
        this.lve_type_code = lve_type_code;
    }

    public String getLve_type_desc() {
        return lve_type_desc;
    }

    public void setLve_type_desc(String lve_type_desc) {
        this.lve_type_desc = lve_type_desc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
