package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by dmin on 6/13/2018.
 */

public class Sub_Department_Model {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("DEPARTMENTID")
    @Expose
    private Integer dEPARTMENTID;
    @SerializedName("DEPARTMENTDETAIL")
    @Expose
    private String dEPARTMENTDETAIL;
//    @SerializedName("CREATEDON")
//    @Expose
//    private String cREATEDON;
//    @SerializedName("ISACTIVE")
//    @Expose
//    private Boolean iSACTIVE;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getDEPARTMENTID() {
        return dEPARTMENTID;
    }

    public void setDEPARTMENTID(Integer dEPARTMENTID) {
        this.dEPARTMENTID = dEPARTMENTID;
    }

    public String getDEPARTMENTDETAIL() {
        return dEPARTMENTDETAIL;
    }

    public void setDEPARTMENTDETAIL(String dEPARTMENTDETAIL) {
        this.dEPARTMENTDETAIL = dEPARTMENTDETAIL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sub_Department_Model)) return false;
        Sub_Department_Model that = (Sub_Department_Model) o;
        return Objects.equals(iD, that.iD) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iD, dEPARTMENTID, dEPARTMENTDETAIL);
    }
    //    public String getCREATEDON() {
//        return cREATEDON;
//    }
//
//    public void setCREATEDON(String cREATEDON) {
//        this.cREATEDON = cREATEDON;
//    }
//
//    public Boolean getISACTIVE() {
//        return iSACTIVE;
//    }
//
//    public void setISACTIVE(Boolean iSACTIVE) {
//        this.iSACTIVE = iSACTIVE;
//    }
}
