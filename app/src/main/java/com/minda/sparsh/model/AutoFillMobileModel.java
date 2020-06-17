package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutoFillMobileModel {
    @SerializedName("visitorid")
    @Expose
    private Integer visitorid;
    @SerializedName("VisitoCatID")
    @Expose
    private Integer visitoCatID;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("EmailID")
    @Expose
    private String emailID;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("Blocked")
    @Expose
    private Boolean blocked;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("IDProof")
    @Expose
    private String iDProof;
    @SerializedName("Otp")
    @Expose
    private String otp;

    public Integer getVisitorid() {
        return visitorid;
    }

    public void setVisitorid(Integer visitorid) {
        this.visitorid = visitorid;
    }

    public Integer getVisitoCatID() {
        return visitoCatID;
    }

    public void setVisitoCatID(Integer visitoCatID) {
        this.visitoCatID = visitoCatID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIDProof() {
        return iDProof;
    }

    public void setIDProof(String iDProof) {
        this.iDProof = iDProof;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
