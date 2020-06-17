package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IAMGetDomainModel {


    @SerializedName("DomainID")
    @Expose
    private Integer domainID;
    @SerializedName("DomainName")
    @Expose
    private String domainName;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("ModifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("ModifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @SerializedName("DFH")
    @Expose
    private String dFH;
    @SerializedName("CEO")
    @Expose
    private String cEO;
    @SerializedName("DomainAuth")
    @Expose
    private Object domainAuth;
    @SerializedName("ReckActive")
    @Expose
    private Boolean reckActive;


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


    public Integer getDomainID() {
        return domainID;
    }

    public void setDomainID(Integer domainID) {
        this.domainID = domainID;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getDFH() {
        return dFH;
    }

    public void setDFH(String dFH) {
        this.dFH = dFH;
    }

    public String getCEO() {
        return cEO;
    }

    public void setCEO(String cEO) {
        this.cEO = cEO;
    }

    public Object getDomainAuth() {
        return domainAuth;
    }

    public void setDomainAuth(Object domainAuth) {
        this.domainAuth = domainAuth;
    }

    public Boolean getReckActive() {
        return reckActive;
    }

    public void setReckActive(Boolean reckActive) {
        this.reckActive = reckActive;
    }

}
