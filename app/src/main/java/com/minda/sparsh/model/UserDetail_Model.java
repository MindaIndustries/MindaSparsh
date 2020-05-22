package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 6/13/2018.
 */

public class UserDetail_Model {
    @SerializedName("ROLE")
    @Expose
    private String role;
    @SerializedName("PLANTROLE")
    @Expose
    private String pLANTROLE;
    @SerializedName("EMAILID")
    @Expose
    private String emailID;
    @SerializedName("BUSINESS")
    @Expose
    private String bUSINESS;
    @SerializedName("DOMAIN")
    @Expose
    private String dOMAIN;
    @SerializedName("UNITCODE")
    @Expose
    private String uNITCODE;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPLANTROLE() {
        return pLANTROLE;
    }

    public void setPLANTROLE(String pLANTROLE) {
        this.pLANTROLE = pLANTROLE;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getBUSINESS() {
        return bUSINESS;
    }

    public void setBUSINESS(String bUSINESS) {
        this.bUSINESS = bUSINESS;
    }

    public String getDOMAIN() {
        return dOMAIN;
    }

    public void setDOMAIN(String dOMAIN) {
        this.dOMAIN = dOMAIN;
    }

    public String getUNITCODE() {
        return uNITCODE;
    }

    public void setUNITCODE(String uNITCODE) {
        this.uNITCODE = uNITCODE;
    }
}
