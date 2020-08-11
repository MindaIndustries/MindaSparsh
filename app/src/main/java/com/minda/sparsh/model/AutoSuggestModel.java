package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class AutoSuggestModel {

    @SerializedName("EmpName")
    String EmpName;
    @SerializedName("EmpCode")
    String EmpCode;
    @SerializedName("UnitCode")
    String UnitCode;

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getEmpCode() {
        return EmpCode;
    }

    public void setEmpCode(String empCode) {
        EmpCode = empCode;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        UnitCode = unitCode;
    }
}
