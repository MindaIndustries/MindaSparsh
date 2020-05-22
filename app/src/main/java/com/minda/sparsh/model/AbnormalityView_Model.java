package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 6/7/2018.
 */

public class AbnormalityView_Model {
    public String getUploadedBy() {
        return UploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        UploadedBy = uploadedBy;
    }


    @SerializedName("UploadedBy")
    @Expose
    private String UploadedBy;

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    @SerializedName("UpdatedBy")

    @Expose
    private String UpdatedBy;

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Groups")
    @Expose
    private String groups;
    @SerializedName("Domain")
    @Expose
    private String domain;

    public String getImplementationDate() {
        return ImplementationDate;
    }

    public void setImplementationDate(String implementationDate) {
        ImplementationDate = implementationDate;
    }

    public String getTargetDate() {
        return TargetDate;
    }

    public void setTargetDate(String targetDate) {
        TargetDate = targetDate;
    }

    @SerializedName("TargetDate")

    @Expose
    private String TargetDate;



    @SerializedName("Business")

    @Expose
    private String business;
    @SerializedName("ImplementationDate")
    @Expose
    private String ImplementationDate;


    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    @SerializedName("Plant")
    @Expose
    private String plant;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    @SerializedName("Description")

    @Expose
    private String description;
    @SerializedName("Benefits")
    @Expose
    private String benefits;
    @SerializedName("AbnormalityDate")
    @Expose
    private String abnormalityDate;

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getPlantName() {
        return PlantName;
    }

    public void setPlantName(String plantName) {
        PlantName = plantName;
    }

    @SerializedName("IsActive")
    @Expose

    private Boolean isActive;


    @SerializedName("Status")
    @Expose
    private Boolean Status;


    @SerializedName("Action")
    @Expose
    private String Action;

    @SerializedName("BusinessName")
    @Expose
    private String BusinessName;
    @SerializedName("DepartmentName")
    @Expose
    private String DepartmentName;
    @SerializedName("PlantName")
    @Expose
    private String PlantName;








    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getAbnormalityDate() {
        return abnormalityDate;
    }

    public void setAbnormalityDate(String abnormalityDate) {
        this.abnormalityDate = abnormalityDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
