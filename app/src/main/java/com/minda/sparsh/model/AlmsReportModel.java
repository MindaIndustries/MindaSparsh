package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class AlmsReportModel {
    @SerializedName("DAY_IN")
    String DAY_IN;
    @SerializedName("DAY_OUT")
    String DAY_OUT;
    @SerializedName("STAT")
    String STAT;
    @SerializedName("STATUS2")
    String STATUS2;
    @SerializedName("EDATE")
    String EDATE;
    @SerializedName("DAYNAME")
    String DAYNAME;
    @SerializedName("TotalPWD")
    String TotalPWD;
    @SerializedName("TotalPD")
    String TotalPD;
    @SerializedName("TotalAD")
    String TotalAD;
    @SerializedName("TotalLD")
    String TotalLD;
    @SerializedName("Msg")
    String Msg;

    public String getDAY_IN() {
        return DAY_IN;
    }

    public void setDAY_IN(String DAY_IN) {
        this.DAY_IN = DAY_IN;
    }

    public String getDAY_OUT() {
        return DAY_OUT;
    }

    public void setDAY_OUT(String DAY_OUT) {
        this.DAY_OUT = DAY_OUT;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getSTATUS2() {
        return STATUS2;
    }

    public void setSTATUS2(String STATUS2) {
        this.STATUS2 = STATUS2;
    }

    public String getEDATE() {
        return EDATE;
    }

    public void setEDATE(String EDATE) {
        this.EDATE = EDATE;
    }

    public String getDAYNAME() {
        return DAYNAME;
    }

    public void setDAYNAME(String DAYNAME) {
        this.DAYNAME = DAYNAME;
    }

    public String getTotalPWD() {
        return TotalPWD;
    }

    public void setTotalPWD(String totalPWD) {
        TotalPWD = totalPWD;
    }

    public String getTotalPD() {
        return TotalPD;
    }

    public void setTotalPD(String totalPD) {
        TotalPD = totalPD;
    }

    public String getTotalAD() {
        return TotalAD;
    }

    public void setTotalAD(String totalAD) {
        TotalAD = totalAD;
    }

    public String getTotalLD() {
        return TotalLD;
    }

    public void setTotalLD(String totalLD) {
        TotalLD = totalLD;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
