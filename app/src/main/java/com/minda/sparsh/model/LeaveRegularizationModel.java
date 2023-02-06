package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveRegularizationModel {

    @SerializedName("ReqNo")
    String ReqNo;
    @SerializedName("ReqType")
    String ReqType;
    @SerializedName("ReqTypeDesc")
    String ReqTypeDesc;
    @SerializedName("EmpNo")
    String EmpNo;
    @SerializedName("empname")
    String empname;
    @SerializedName("dept")
    String dept;
    @SerializedName("AuthEmpName")
    String AuthEmpName;
    @SerializedName("FromDate")
    String FromDate;
    @SerializedName("ToDate")
    String ToDate;
    @SerializedName("NoOfDays")
    String NoOfDays;
    @SerializedName("Remarks")
    String Remarks;
    @SerializedName("CreatedOn")
    String CreatedOn;
    @SerializedName("AuthPerson")
    String AuthPerson;
    @SerializedName("ActiveDesc")
    String ActiveDesc;
    @SerializedName("ReqAuthDesc")
    String ReqAuthDesc;


    public String getReqNo() {
        return ReqNo;
    }

    public void setReqNo(String reqNo) {
        ReqNo = reqNo;
    }

    public String getReqType() {
        return ReqType;
    }

    public void setReqType(String reqType) {
        ReqType = reqType;
    }

    public String getReqTypeDesc() {
        return ReqTypeDesc;
    }

    public void setReqTypeDesc(String reqTypeDesc) {
        ReqTypeDesc = reqTypeDesc;
    }

    public String getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(String empNo) {
        EmpNo = empNo;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getAuthEmpName() {
        return AuthEmpName;
    }

    public void setAuthEmpName(String authEmpName) {
        AuthEmpName = authEmpName;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        NoOfDays = noOfDays;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getAuthPerson() {
        return AuthPerson;
    }

    public void setAuthPerson(String authPerson) {
        AuthPerson = authPerson;
    }

    public String getActiveDesc() {
        return ActiveDesc;
    }

    public void setActiveDesc(String activeDesc) {
        ActiveDesc = activeDesc;
    }

    public String getReqAuthDesc() {
        return ReqAuthDesc;
    }

    public void setReqAuthDesc(String reqAuthDesc) {
        ReqAuthDesc = reqAuthDesc;
    }






}
