package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IAMGetBusinessModel {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("DOMAINID")
    @Expose
    private Integer dOMAINID;
    @SerializedName("BUSINESS")
    @Expose
    private String bUSINESS;
    @SerializedName("CREATEDON")
    @Expose
    private String cREATEDON;
    @SerializedName("ISACTIVE")
    @Expose
    private Boolean iSACTIVE;


    @SerializedName("isSelected")
    @Expose
    private boolean isSelected;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getDOMAINID() {
        return dOMAINID;
    }

    public void setDOMAINID(Integer dOMAINID) {
        this.dOMAINID = dOMAINID;
    }

    public String getBUSINESS() {
        return bUSINESS;
    }

    public void setBUSINESS(String bUSINESS) {
        this.bUSINESS = bUSINESS;
    }

    public String getCREATEDON() {
        return cREATEDON;
    }

    public void setCREATEDON(String cREATEDON) {
        this.cREATEDON = cREATEDON;
    }

    public Boolean getISACTIVE() {
        return iSACTIVE;
    }

    public void setISACTIVE(Boolean iSACTIVE) {
        this.iSACTIVE = iSACTIVE;
    }

}
