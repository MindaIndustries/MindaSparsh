package com.minda.sparsh.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 6/7/2018.
 */

public class AbnormalityView_Model implements Parcelable {
    @SerializedName("UploadedBy")
    @Expose
    private String UploadedBy;
    @SerializedName("UpdatedBy")
    @Expose
    private String UpdatedBy;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Groups")
    @Expose
    private String groups;
    @SerializedName("Domain1")
    @Expose
    private String domain;
    @SerializedName("Category")
    @Expose
    String Category;
    @SerializedName("TargetDate")
    @Expose
    private String TargetDate;
    @SerializedName("Business")
    @Expose
    private String business;
    @SerializedName("ImplementationDate")
    @Expose
    private String ImplementationDate;
    @SerializedName("Plant")
    @Expose
    private String plant;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Benefits")
    @Expose
    private String benefits;
    @SerializedName("AbnormalityDate")
    @Expose
    private String abnormalityDate;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("Status")
    @Expose
    private Boolean Status;
    @SerializedName("Action")
    @Expose
    private String Action;
    @SerializedName("BusinessName1")
    @Expose
    private String BusinessName;
    @SerializedName("DepartmentName")
    @Expose
    private String DepartmentName;
    @SerializedName("PlantName")
    @Expose
    private String PlantName;
    @SerializedName("Flag")
    String Flag;
    @SerializedName("functionId")
    String functionId;
    @SerializedName("Assignedto")
    String Assignedto;

    protected AbnormalityView_Model(Parcel in) {
        UploadedBy = in.readString();
        UpdatedBy = in.readString();
        if (in.readByte() == 0) {
            iD = null;
        } else {
            iD = in.readInt();
        }
        groups = in.readString();
        domain = in.readString();
        Category = in.readString();
        TargetDate = in.readString();
        business = in.readString();
        ImplementationDate = in.readString();
        plant = in.readString();
        department = in.readString();
        imagePath = in.readString();
        description = in.readString();
        benefits = in.readString();
        abnormalityDate = in.readString();
        byte tmpIsActive = in.readByte();
        isActive = tmpIsActive == 0 ? null : tmpIsActive == 1;
        byte tmpStatus = in.readByte();
        Status = tmpStatus == 0 ? null : tmpStatus == 1;
        Action = in.readString();
        BusinessName = in.readString();
        DepartmentName = in.readString();
        PlantName = in.readString();
        Flag = in.readString();
        functionId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UploadedBy);
        dest.writeString(UpdatedBy);
        if (iD == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(iD);
        }
        dest.writeString(groups);
        dest.writeString(domain);
        dest.writeString(Category);
        dest.writeString(TargetDate);
        dest.writeString(business);
        dest.writeString(ImplementationDate);
        dest.writeString(plant);
        dest.writeString(department);
        dest.writeString(imagePath);
        dest.writeString(description);
        dest.writeString(benefits);
        dest.writeString(abnormalityDate);
        dest.writeByte((byte) (isActive == null ? 0 : isActive ? 1 : 2));
        dest.writeByte((byte) (Status == null ? 0 : Status ? 1 : 2));
        dest.writeString(Action);
        dest.writeString(BusinessName);
        dest.writeString(DepartmentName);
        dest.writeString(PlantName);
        dest.writeString(Flag);
        dest.writeString(functionId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AbnormalityView_Model> CREATOR = new Creator<AbnormalityView_Model>() {
        @Override
        public AbnormalityView_Model createFromParcel(Parcel in) {
            return new AbnormalityView_Model(in);
        }

        @Override
        public AbnormalityView_Model[] newArray(int size) {
            return new AbnormalityView_Model[size];
        }
    };

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getImplementationDate() {
        return ImplementationDate;
    }

    public String getUploadedBy() {
        return UploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        UploadedBy = uploadedBy;
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

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getAssignedto() {
        return Assignedto;
    }

    public void setAssignedto(String assignedto) {
        Assignedto = assignedto;
    }
}
