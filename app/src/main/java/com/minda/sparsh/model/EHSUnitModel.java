package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class EHSUnitModel {
    @SerializedName("UnitCode")
    String UnitCode;
    @SerializedName("UnitName")
    String UnitName;



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
}
