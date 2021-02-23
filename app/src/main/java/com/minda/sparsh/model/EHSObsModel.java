package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class EHSObsModel {
    @SerializedName("UnitName")
    String UnitName;
    @SerializedName("Location")
    String Location;
    @SerializedName("CatName")
    String CatName;
    @SerializedName("USName")
    String USName;
    @SerializedName("OBName")
    String OBName;
    @SerializedName("ID")
    String ID;
    @SerializedName("ActNo")
    String ActNo;
    @SerializedName("ActDate")
    String ActDate;
    @SerializedName("SubmitBy")
    String SubmitBy;
    @SerializedName("HOD")
    String HOD;
    @SerializedName("UnitSafetyOfficer")
    String UnitSafetyOfficer;
    @SerializedName("UnitCode")
    String UnitCode;
    @SerializedName("Description")
    String Description;
    @SerializedName("CreatedOn")
    String CreatedOn;
    @SerializedName("Attachment")
    String Attachment;
    @SerializedName("Status")
    String Status;
    @SerializedName("SubCategoryID")
    String SubCategoryID;
    @SerializedName("CategoryID")
    String CategoryID;
    @SerializedName("ObservationID")
    String ObservationID;
    @SerializedName("IncidenceTime")
    String IncidenceTime;
    @SerializedName("IncidentceAction")
    String IncidentceAction;
    @SerializedName("Assigned")
    String Assigned;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getActNo() {
        return ActNo;
    }

    public void setActNo(String actNo) {
        ActNo = actNo;
    }

    public String getActDate() {
        return ActDate;
    }

    public void setActDate(String actDate) {
        ActDate = actDate;
    }

    public String getSubmitBy() {
        return SubmitBy;
    }

    public void setSubmitBy(String submitBy) {
        SubmitBy = submitBy;
    }

    public String getHOD() {
        return HOD;
    }

    public void setHOD(String HOD) {
        this.HOD = HOD;
    }

    public String getUnitSafetyOfficer() {
        return UnitSafetyOfficer;
    }

    public void setUnitSafetyOfficer(String unitSafetyOfficer) {
        UnitSafetyOfficer = unitSafetyOfficer;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        UnitCode = unitCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getAttachment() {
        return Attachment;
    }

    public void setAttachment(String attachment) {
        Attachment = attachment;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }


    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public String getUSName() {
        return USName;
    }

    public void setUSName(String USName) {
        this.USName = USName;
    }

    public String getOBName() {
        return OBName;
    }

    public void setOBName(String OBName) {
        this.OBName = OBName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSubCategoryID() {
        return SubCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        SubCategoryID = subCategoryID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getObservationID() {
        return ObservationID;
    }

    public void setObservationID(String observationID) {
        ObservationID = observationID;
    }

    public String getIncidenceTime() {
        return IncidenceTime;
    }

    public void setIncidenceTime(String incidenceTime) {
        IncidenceTime = incidenceTime;
    }

    public String getIncidentceAction() {
        return IncidentceAction;
    }

    public void setIncidentceAction(String incidentceAction) {
        IncidentceAction = incidentceAction;
    }

    public String getAssigned() {
        return Assigned;
    }

    public void setAssigned(String assigned) {
        Assigned = assigned;
    }
}
