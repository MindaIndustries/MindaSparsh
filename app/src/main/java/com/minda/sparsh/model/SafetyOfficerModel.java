package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class SafetyOfficerModel {
    @SerializedName("UnitOfficer")
    String UnitOfficer;
    @SerializedName("UnitCode")
    String UnitCode;
    @SerializedName("UnitName")
    String UnitName;
    @SerializedName("USName")
    String USName;

    public String getUnitOfficer() {
        return UnitOfficer;
    }

    public void setUnitOfficer(String unitOfficer) {
        UnitOfficer = unitOfficer;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        UnitCode = unitCode;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getUSName() {
        return USName;
    }

    public void setUSName(String USName) {
        this.USName = USName;
    }
}
