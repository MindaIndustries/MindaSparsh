package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 6/7/2018.
 */

public class Department_Model {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("DEPARTMENT")
    @Expose
    private String dEPARTMENT;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getDEPARTMENT() {
        return dEPARTMENT;
    }

    public void setDEPARTMENT(String dEPARTMENT) {
        this.dEPARTMENT = dEPARTMENT;
    }
}
