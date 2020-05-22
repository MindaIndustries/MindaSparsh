package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 4/25/2018.
 */

public class BannarModel {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("IMAGE")
    @Expose
    private String iMAGE;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getIMAGE() {
        return iMAGE;
    }

    public void setIMAGE(String iMAGE) {
        this.iMAGE = iMAGE;
    }
}
