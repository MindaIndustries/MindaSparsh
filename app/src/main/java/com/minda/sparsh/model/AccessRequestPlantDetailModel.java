package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessRequestPlantDetailModel {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("AccessRequestNo")
    @Expose
    private String accessRequestNo;
    @SerializedName("DomainId")
    @Expose
    private String domainId;
    @SerializedName("BusinessId")
    @Expose
    private String businessId;
    @SerializedName("UnitCode")
    @Expose
    private String unitCode;
    @SerializedName("DomainName")
    @Expose
    private String domainName;
    @SerializedName("BUSINESS")
    @Expose
    private String bUSINESS;
    @SerializedName("UnitName")
    @Expose
    private String unitName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessRequestNo() {
        return accessRequestNo;
    }

    public void setAccessRequestNo(String accessRequestNo) {
        this.accessRequestNo = accessRequestNo;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getBUSINESS() {
        return bUSINESS;
    }

    public void setBUSINESS(String bUSINESS) {
        this.bUSINESS = bUSINESS;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

}
