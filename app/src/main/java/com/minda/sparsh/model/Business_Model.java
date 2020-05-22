package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 6/7/2018.
 */

public class Business_Model {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("BUSINESS")
    @Expose
    private String bUSINESS;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getBUSINESS() {
        return bUSINESS;
    }

    public void setBUSINESS(String bUSINESS) {
        this.bUSINESS = bUSINESS;
    }
}
