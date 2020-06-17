package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IAMGetListOfNames {
    @SerializedName("EMP")
    @Expose
    private String eMP;

    public String getEMP() {
        return eMP;
    }

    public void setEMP(String eMP) {
        this.eMP = eMP;
    }

}
