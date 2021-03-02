package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class IAMGetPlantModel implements Serializable {

    @SerializedName("UnitsID")
    @Expose
    private Integer unitsID;
    @SerializedName("UnitCode")
    @Expose
    private String unitCode;
    @SerializedName("UnitName")
    @Expose
    private String unitName;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("HRHead")
    @Expose
    private String hRHead;
    @SerializedName("HREmpCode")
    @Expose
    private String hREmpCode;
    @SerializedName("HREmailID")
    @Expose
    private String hREmailID;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @SerializedName("ModifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("ModifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("PlantHead")
    @Expose
    private String plantHead;
    @SerializedName("BusinessHead")
    @Expose
    private String businessHead;
    @SerializedName("DomainID")
    @Expose
    private Integer domainID;
    @SerializedName("UFH")
    @Expose
    private String uFH;
    @SerializedName("PMSElg")
    @Expose
    private Boolean pMSElg;
    @SerializedName("AuditComp")
    @Expose
    private Object auditComp;
    @SerializedName("Auditor")
    @Expose
    private Object auditor;
    @SerializedName("UnitCEO")
    @Expose
    private String unitCEO;
    @SerializedName("BusinessID")
    @Expose
    private Integer businessID;
    @SerializedName("PlantCode")
    @Expose
    private String plantCode;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("OperationHead")
    @Expose
    private String operationHead;
    @SerializedName("Business")
    @Expose
    private Object business;
    @SerializedName("EstablisedOn")
    @Expose
    private String establisedOn;
    @SerializedName("AnnualDay")
    @Expose
    private String annualDay;
    @SerializedName("SapUnit")
    @Expose
    private Boolean sapUnit;
    @SerializedName("ManPower")
    @Expose
    private Integer manPower;
    @SerializedName("NewJoinee")
    @Expose
    private Integer newJoinee;
    @SerializedName("JoineeAssociate")
    @Expose
    private Integer joineeAssociate;
    @SerializedName("AttritionofStaff")
    @Expose
    private String attritionofStaff;
    @SerializedName("Attritionofassociate")
    @Expose
    private String attritionofassociate;
    @SerializedName("HeadCountStaff")
    @Expose
    private Integer headCountStaff;
    @SerializedName("HeadCoutRollAssociate")
    @Expose
    private Integer headCoutRollAssociate;
    @SerializedName("ConstructualAssociate")
    @Expose
    private Integer constructualAssociate;
    @SerializedName("talHeadCountAssociate")
    @Expose
    private Integer talHeadCountAssociate;
    @SerializedName("MaleFemaleStaffRatio")
    @Expose
    private String maleFemaleStaffRatio;
    @SerializedName("MaleFemaleAssociateRatio")
    @Expose
    private String maleFemaleAssociateRatio;
    @SerializedName("ManpowerCost")
    @Expose
    private String manpowerCost;
    @SerializedName("DiffrentlyAbled")
    @Expose
    private Integer diffrentlyAbled;
    @SerializedName("ManagementEmpCode")
    @Expose
    private String managementEmpCode;
    @SerializedName("ManpowerEmpCode")
    @Expose
    private String manpowerEmpCode;
    @SerializedName("ManufacturingEmpCode")
    @Expose
    private String manufacturingEmpCode;
    @SerializedName("MarketingEmpCode")
    @Expose
    private String marketingEmpCode;
    @SerializedName("MaterialEmpCode")
    @Expose
    private String materialEmpCode;
    @SerializedName("MoneyEmpCode")
    @Expose
    private String moneyEmpCode;
    @SerializedName("ETEmpCode")
    @Expose
    private String eTEmpCode;
    @SerializedName("ITEmpCode")
    @Expose
    private String iTEmpCode;
    @SerializedName("ManagementStaff")
    @Expose
    private Integer managementStaff;
    @SerializedName("ManpowerStaff")
    @Expose
    private Integer manpowerStaff;
    @SerializedName("ManufacturingStaff")
    @Expose
    private Integer manufacturingStaff;
    @SerializedName("MarketingStaff")
    @Expose
    private Integer marketingStaff;
    @SerializedName("MaterialStaff")
    @Expose
    private Integer materialStaff;
    @SerializedName("MoneyStaff")
    @Expose
    private Integer moneyStaff;
    @SerializedName("EngineeringStaff")
    @Expose
    private Integer engineeringStaff;
    @SerializedName("ITStaff")
    @Expose
    private Integer iTStaff;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("BusinessHrHead")
    @Expose
    private String businessHrHead;
    @SerializedName("ManuFacturingHead")
    @Expose
    private String manuFacturingHead;
    @SerializedName("DomainHrHead")
    @Expose
    private String domainHrHead;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("Busadda")
    @Expose
    private String busadda;
    @SerializedName("Railway")
    @Expose
    private String railway;
    @SerializedName("Airport")
    @Expose
    private String airport;
    @SerializedName("BusinessFinanceHead")
    @Expose
    private String businessFinanceHead;
    @SerializedName("CMD")
    @Expose
    private String cMD;
    @SerializedName("SaveAsDraft")
    @Expose
    private Boolean saveAsDraft;
    @SerializedName("UnitRecnor")
    @Expose
    private Boolean unitRecnor;
    @SerializedName("Strategy")
    @Expose
    private String strategy;
    @SerializedName("HRHeadEmpCode")
    @Expose
    private String hRHeadEmpCode;
    @SerializedName("StrategyStaff")
    @Expose
    private Integer strategyStaff;
    @SerializedName("ManagementAssociateNo")
    @Expose
    private Integer managementAssociateNo;
    @SerializedName("ManPowerAssociateNo")
    @Expose
    private Integer manPowerAssociateNo;
    @SerializedName("ManufacturingAssociateNo")
    @Expose
    private Integer manufacturingAssociateNo;
    @SerializedName("MarketingAssociateNo")
    @Expose
    private Integer marketingAssociateNo;
    @SerializedName("MoneyAssociateNo")
    @Expose
    private Integer moneyAssociateNo;
    @SerializedName("MaterialAssociateNo")
    @Expose
    private Integer materialAssociateNo;
    @SerializedName("EnggAssociateNo")
    @Expose
    private Integer enggAssociateNo;
    @SerializedName("ITAssociateNo")
    @Expose
    private Integer iTAssociateNo;
    @SerializedName("StrategyAssociateNo")
    @Expose
    private Integer strategyAssociateNo;

    public Integer getUnitsID() {
        return unitsID;
    }

    public void setUnitsID(Integer unitsID) {
        this.unitsID = unitsID;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getHRHead() {
        return hRHead;
    }

    public void setHRHead(String hRHead) {
        this.hRHead = hRHead;
    }

    public String getHREmpCode() {
        return hREmpCode;
    }

    public void setHREmpCode(String hREmpCode) {
        this.hREmpCode = hREmpCode;
    }

    public String getHREmailID() {
        return hREmailID;
    }

    public void setHREmailID(String hREmailID) {
        this.hREmailID = hREmailID;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPlantHead() {
        return plantHead;
    }

    public void setPlantHead(String plantHead) {
        this.plantHead = plantHead;
    }

    public String getBusinessHead() {
        return businessHead;
    }

    public void setBusinessHead(String businessHead) {
        this.businessHead = businessHead;
    }

    public Integer getDomainID() {
        return domainID;
    }

    public void setDomainID(Integer domainID) {
        this.domainID = domainID;
    }

    public String getUFH() {
        return uFH;
    }

    public void setUFH(String uFH) {
        this.uFH = uFH;
    }

    public Boolean getPMSElg() {
        return pMSElg;
    }

    public void setPMSElg(Boolean pMSElg) {
        this.pMSElg = pMSElg;
    }

    public Object getAuditComp() {
        return auditComp;
    }

    public void setAuditComp(Object auditComp) {
        this.auditComp = auditComp;
    }

    public Object getAuditor() {
        return auditor;
    }

    public void setAuditor(Object auditor) {
        this.auditor = auditor;
    }

    public String getUnitCEO() {
        return unitCEO;
    }

    public void setUnitCEO(String unitCEO) {
        this.unitCEO = unitCEO;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOperationHead() {
        return operationHead;
    }

    public void setOperationHead(String operationHead) {
        this.operationHead = operationHead;
    }

    public Object getBusiness() {
        return business;
    }

    public void setBusiness(Object business) {
        this.business = business;
    }

    public String getEstablisedOn() {
        return establisedOn;
    }

    public void setEstablisedOn(String establisedOn) {
        this.establisedOn = establisedOn;
    }

    public String getAnnualDay() {
        return annualDay;
    }

    public void setAnnualDay(String annualDay) {
        this.annualDay = annualDay;
    }

    public Boolean getSapUnit() {
        return sapUnit;
    }

    public void setSapUnit(Boolean sapUnit) {
        this.sapUnit = sapUnit;
    }

    public Integer getManPower() {
        return manPower;
    }

    public void setManPower(Integer manPower) {
        this.manPower = manPower;
    }

    public Integer getNewJoinee() {
        return newJoinee;
    }

    public void setNewJoinee(Integer newJoinee) {
        this.newJoinee = newJoinee;
    }

    public Integer getJoineeAssociate() {
        return joineeAssociate;
    }

    public void setJoineeAssociate(Integer joineeAssociate) {
        this.joineeAssociate = joineeAssociate;
    }

    public String getAttritionofStaff() {
        return attritionofStaff;
    }

    public void setAttritionofStaff(String attritionofStaff) {
        this.attritionofStaff = attritionofStaff;
    }

    public String getAttritionofassociate() {
        return attritionofassociate;
    }

    public void setAttritionofassociate(String attritionofassociate) {
        this.attritionofassociate = attritionofassociate;
    }

    public Integer getHeadCountStaff() {
        return headCountStaff;
    }

    public void setHeadCountStaff(Integer headCountStaff) {
        this.headCountStaff = headCountStaff;
    }

    public Integer getHeadCoutRollAssociate() {
        return headCoutRollAssociate;
    }

    public void setHeadCoutRollAssociate(Integer headCoutRollAssociate) {
        this.headCoutRollAssociate = headCoutRollAssociate;
    }

    public Integer getConstructualAssociate() {
        return constructualAssociate;
    }

    public void setConstructualAssociate(Integer constructualAssociate) {
        this.constructualAssociate = constructualAssociate;
    }

    public Integer getTalHeadCountAssociate() {
        return talHeadCountAssociate;
    }

    public void setTalHeadCountAssociate(Integer talHeadCountAssociate) {
        this.talHeadCountAssociate = talHeadCountAssociate;
    }

    public String getMaleFemaleStaffRatio() {
        return maleFemaleStaffRatio;
    }

    public void setMaleFemaleStaffRatio(String maleFemaleStaffRatio) {
        this.maleFemaleStaffRatio = maleFemaleStaffRatio;
    }

    public String getMaleFemaleAssociateRatio() {
        return maleFemaleAssociateRatio;
    }

    public void setMaleFemaleAssociateRatio(String maleFemaleAssociateRatio) {
        this.maleFemaleAssociateRatio = maleFemaleAssociateRatio;
    }

    public String getManpowerCost() {
        return manpowerCost;
    }

    public void setManpowerCost(String manpowerCost) {
        this.manpowerCost = manpowerCost;
    }

    public Integer getDiffrentlyAbled() {
        return diffrentlyAbled;
    }

    public void setDiffrentlyAbled(Integer diffrentlyAbled) {
        this.diffrentlyAbled = diffrentlyAbled;
    }

    public String getManagementEmpCode() {
        return managementEmpCode;
    }

    public void setManagementEmpCode(String managementEmpCode) {
        this.managementEmpCode = managementEmpCode;
    }

    public String getManpowerEmpCode() {
        return manpowerEmpCode;
    }

    public void setManpowerEmpCode(String manpowerEmpCode) {
        this.manpowerEmpCode = manpowerEmpCode;
    }

    public String getManufacturingEmpCode() {
        return manufacturingEmpCode;
    }

    public void setManufacturingEmpCode(String manufacturingEmpCode) {
        this.manufacturingEmpCode = manufacturingEmpCode;
    }

    public String getMarketingEmpCode() {
        return marketingEmpCode;
    }

    public void setMarketingEmpCode(String marketingEmpCode) {
        this.marketingEmpCode = marketingEmpCode;
    }

    public String getMaterialEmpCode() {
        return materialEmpCode;
    }

    public void setMaterialEmpCode(String materialEmpCode) {
        this.materialEmpCode = materialEmpCode;
    }

    public String getMoneyEmpCode() {
        return moneyEmpCode;
    }

    public void setMoneyEmpCode(String moneyEmpCode) {
        this.moneyEmpCode = moneyEmpCode;
    }

    public String getETEmpCode() {
        return eTEmpCode;
    }

    public void setETEmpCode(String eTEmpCode) {
        this.eTEmpCode = eTEmpCode;
    }

    public String getITEmpCode() {
        return iTEmpCode;
    }

    public void setITEmpCode(String iTEmpCode) {
        this.iTEmpCode = iTEmpCode;
    }

    public Integer getManagementStaff() {
        return managementStaff;
    }

    public void setManagementStaff(Integer managementStaff) {
        this.managementStaff = managementStaff;
    }

    public Integer getManpowerStaff() {
        return manpowerStaff;
    }

    public void setManpowerStaff(Integer manpowerStaff) {
        this.manpowerStaff = manpowerStaff;
    }

    public Integer getManufacturingStaff() {
        return manufacturingStaff;
    }

    public void setManufacturingStaff(Integer manufacturingStaff) {
        this.manufacturingStaff = manufacturingStaff;
    }

    public Integer getMarketingStaff() {
        return marketingStaff;
    }

    public void setMarketingStaff(Integer marketingStaff) {
        this.marketingStaff = marketingStaff;
    }

    public Integer getMaterialStaff() {
        return materialStaff;
    }

    public void setMaterialStaff(Integer materialStaff) {
        this.materialStaff = materialStaff;
    }

    public Integer getMoneyStaff() {
        return moneyStaff;
    }

    public void setMoneyStaff(Integer moneyStaff) {
        this.moneyStaff = moneyStaff;
    }

    public Integer getEngineeringStaff() {
        return engineeringStaff;
    }

    public void setEngineeringStaff(Integer engineeringStaff) {
        this.engineeringStaff = engineeringStaff;
    }

    public Integer getITStaff() {
        return iTStaff;
    }

    public void setITStaff(Integer iTStaff) {
        this.iTStaff = iTStaff;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBusinessHrHead() {
        return businessHrHead;
    }

    public void setBusinessHrHead(String businessHrHead) {
        this.businessHrHead = businessHrHead;
    }

    public String getManuFacturingHead() {
        return manuFacturingHead;
    }

    public void setManuFacturingHead(String manuFacturingHead) {
        this.manuFacturingHead = manuFacturingHead;
    }

    public String getDomainHrHead() {
        return domainHrHead;
    }

    public void setDomainHrHead(String domainHrHead) {
        this.domainHrHead = domainHrHead;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getBusadda() {
        return busadda;
    }

    public void setBusadda(String busadda) {
        this.busadda = busadda;
    }

    public String getRailway() {
        return railway;
    }

    public void setRailway(String railway) {
        this.railway = railway;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getBusinessFinanceHead() {
        return businessFinanceHead;
    }

    public void setBusinessFinanceHead(String businessFinanceHead) {
        this.businessFinanceHead = businessFinanceHead;
    }

    public String getCMD() {
        return cMD;
    }

    public void setCMD(String cMD) {
        this.cMD = cMD;
    }

    public Boolean getSaveAsDraft() {
        return saveAsDraft;
    }

    public void setSaveAsDraft(Boolean saveAsDraft) {
        this.saveAsDraft = saveAsDraft;
    }

    public Boolean getUnitRecnor() {
        return unitRecnor;
    }

    public void setUnitRecnor(Boolean unitRecnor) {
        this.unitRecnor = unitRecnor;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getHRHeadEmpCode() {
        return hRHeadEmpCode;
    }

    public void setHRHeadEmpCode(String hRHeadEmpCode) {
        this.hRHeadEmpCode = hRHeadEmpCode;
    }

    public Integer getStrategyStaff() {
        return strategyStaff;
    }

    public void setStrategyStaff(Integer strategyStaff) {
        this.strategyStaff = strategyStaff;
    }

    public Integer getManagementAssociateNo() {
        return managementAssociateNo;
    }

    public void setManagementAssociateNo(Integer managementAssociateNo) {
        this.managementAssociateNo = managementAssociateNo;
    }

    public Integer getManPowerAssociateNo() {
        return manPowerAssociateNo;
    }

    public void setManPowerAssociateNo(Integer manPowerAssociateNo) {
        this.manPowerAssociateNo = manPowerAssociateNo;
    }

    public Integer getManufacturingAssociateNo() {
        return manufacturingAssociateNo;
    }

    public void setManufacturingAssociateNo(Integer manufacturingAssociateNo) {
        this.manufacturingAssociateNo = manufacturingAssociateNo;
    }

    public Integer getMarketingAssociateNo() {
        return marketingAssociateNo;
    }

    public void setMarketingAssociateNo(Integer marketingAssociateNo) {
        this.marketingAssociateNo = marketingAssociateNo;
    }

    public Integer getMoneyAssociateNo() {
        return moneyAssociateNo;
    }

    public void setMoneyAssociateNo(Integer moneyAssociateNo) {
        this.moneyAssociateNo = moneyAssociateNo;
    }

    public Integer getMaterialAssociateNo() {
        return materialAssociateNo;
    }

    public void setMaterialAssociateNo(Integer materialAssociateNo) {
        this.materialAssociateNo = materialAssociateNo;
    }

    public Integer getEnggAssociateNo() {
        return enggAssociateNo;
    }

    public void setEnggAssociateNo(Integer enggAssociateNo) {
        this.enggAssociateNo = enggAssociateNo;
    }

    public Integer getITAssociateNo() {
        return iTAssociateNo;
    }

    public void setITAssociateNo(Integer iTAssociateNo) {
        this.iTAssociateNo = iTAssociateNo;
    }

    public Integer getStrategyAssociateNo() {
        return strategyAssociateNo;
    }

    public void setStrategyAssociateNo(Integer strategyAssociateNo) {
        this.strategyAssociateNo = strategyAssociateNo;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IAMGetPlantModel)) return false;

        IAMGetPlantModel that = (IAMGetPlantModel) obj;

        if ((unitCode!="0" && !unitCode.equals(that.unitCode)))
            return false;


        return true;       }
}