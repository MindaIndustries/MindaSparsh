package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class CheckCompOffValidation {
    @SerializedName("coff_allow")
    Integer coff_allow;
    @SerializedName("COFF_ALLOW")
    Integer COFF_ALLOW;
    @SerializedName("REMARKS")
    String REMARKS;
    @SerializedName("Remarks")
    String Remarks;
    @SerializedName("WORK_HOURS")
    Integer WORK_HOURS;


    public Integer getCoff_allow() {
        return coff_allow;
    }

    public void setCoff_allow(Integer coff_allow) {
        this.coff_allow = coff_allow;
    }

    public Integer getCOFF_ALLOW() {
        return COFF_ALLOW;
    }

    public void setCOFF_ALLOW(Integer COFF_ALLOW) {
        this.COFF_ALLOW = COFF_ALLOW;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public Integer getWORK_HOURS() {
        return WORK_HOURS;
    }

    public void setWORK_HOURS(Integer WORK_HOURS) {
        this.WORK_HOURS = WORK_HOURS;
    }
}
