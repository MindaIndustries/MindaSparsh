package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewAppointmentModel {
    @SerializedName("VisitorID")
    @Expose
    private Integer visitorID;
    @SerializedName("VisitorCatID")
    @Expose
    private Object visitorCatID;
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
    private Object state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
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
    private Object otp;
    @SerializedName("VisitorLogID")
    @Expose
    private Integer visitorLogID;
    @SerializedName("VisitorID1")
    @Expose
    private Integer visitorID1;
    @SerializedName("EmpCode")
    @Expose
    private String empCode;
    @SerializedName("Purpose")
    @Expose
    private String purpose;
    @SerializedName("TimeIn")
    @Expose
    private String timeIn;
    @SerializedName("TimeOut")
    @Expose
    private String timeOut;
    @SerializedName("VisitingDate")
    @Expose
    private String visitingDate;
    @SerializedName("OTP1")
    @Expose
    private Object oTP1;
    @SerializedName("SecurityID")
    @Expose
    private Object securityID;
    @SerializedName("SerialNo")
    @Expose
    private Object serialNo;
    @SerializedName("Active1")
    @Expose
    private Boolean active1;
    @SerializedName("CreatedDate1")
    @Expose
    private String createdDate1;
    @SerializedName("AppointmentDate")
    @Expose
    private String appointmentDate;
    @SerializedName("Type1")
    @Expose
    private Object type1;
    @SerializedName("CheckIn")
    @Expose
    private Object checkIn;
    @SerializedName("CheckOut")
    @Expose
    private Object checkOut;
    @SerializedName("UnitCode")
    @Expose
    private String unitCode;
    @SerializedName("CreatedBy1")
    @Expose
    private Object createdBy1;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("CheckInBy")
    @Expose
    private Object checkInBy;
    @SerializedName("CheckOutBy")
    @Expose
    private Object checkOutBy;
    @SerializedName("EmpMobile")
    @Expose
    private Object empMobile;
    @SerializedName("AppID")
    @Expose
    private Object appID;
    @SerializedName("Deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("ModifiedBy")
    @Expose
    private Object modifiedBy;
    @SerializedName("DeletedBy")
    @Expose
    private Object deletedBy;
    @SerializedName("AddPersons")
    @Expose
    private String addPersons;
    @SerializedName("MainStatus")
    @Expose
    private Boolean MainStatus;

    public Boolean getMainStatus() {
        return MainStatus;
    }

    public void setMainStatus(Boolean mainStatus) {
        MainStatus = mainStatus;
    }


    public String getAppointmentDate1() {
        return AppointmentDate1;
    }

    public void setAppointmentDate1(String appointmentDate1) {
        AppointmentDate1 = appointmentDate1;
    }

    @SerializedName("AppointmentDate1")
    @Expose
    private String AppointmentDate1;

    public Integer getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(Integer visitorID) {
        this.visitorID = visitorID;
    }

    public Object getVisitorCatID() {
        return visitorCatID;
    }

    public void setVisitorCatID(Object visitorCatID) {
        this.visitorCatID = visitorCatID;
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

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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

    public Object getOtp() {
        return otp;
    }

    public void setOtp(Object otp) {
        this.otp = otp;
    }

    public Integer getVisitorLogID() {
        return visitorLogID;
    }

    public void setVisitorLogID(Integer visitorLogID) {
        this.visitorLogID = visitorLogID;
    }

    public Integer getVisitorID1() {
        return visitorID1;
    }

    public void setVisitorID1(Integer visitorID1) {
        this.visitorID1 = visitorID1;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getVisitingDate() {
        return visitingDate;
    }

    public void setVisitingDate(String visitingDate) {
        this.visitingDate = visitingDate;
    }

    public Object getOTP1() {
        return oTP1;
    }

    public void setOTP1(Object oTP1) {
        this.oTP1 = oTP1;
    }

    public Object getSecurityID() {
        return securityID;
    }

    public void setSecurityID(Object securityID) {
        this.securityID = securityID;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Object serialNo) {
        this.serialNo = serialNo;
    }

    public Boolean getActive1() {
        return active1;
    }

    public void setActive1(Boolean active1) {
        this.active1 = active1;
    }

    public String getCreatedDate1() {
        return createdDate1;
    }

    public void setCreatedDate1(String createdDate1) {
        this.createdDate1 = createdDate1;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Object getType1() {
        return type1;
    }

    public void setType1(Object type1) {
        this.type1 = type1;
    }

    public Object getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Object checkIn) {
        this.checkIn = checkIn;
    }

    public Object getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Object checkOut) {
        this.checkOut = checkOut;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Object getCreatedBy1() {
        return createdBy1;
    }

    public void setCreatedBy1(Object createdBy1) {
        this.createdBy1 = createdBy1;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getCheckInBy() {
        return checkInBy;
    }

    public void setCheckInBy(Object checkInBy) {
        this.checkInBy = checkInBy;
    }

    public Object getCheckOutBy() {
        return checkOutBy;
    }

    public void setCheckOutBy(Object checkOutBy) {
        this.checkOutBy = checkOutBy;
    }

    public Object getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(Object empMobile) {
        this.empMobile = empMobile;
    }

    public Object getAppID() {
        return appID;
    }

    public void setAppID(Object appID) {
        this.appID = appID;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Object getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Object modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getAddPersons() {
        return addPersons;
    }

    public void setAddPersons(String addPersons) {
        this.addPersons = addPersons;
    }

}
