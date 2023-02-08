package com.minda.sparsh.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LeaveRegularizationModel implements Parcelable {

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


    protected LeaveRegularizationModel(Parcel in) {
        ReqNo = in.readString();
        ReqType = in.readString();
        ReqTypeDesc = in.readString();
        EmpNo = in.readString();
        empname = in.readString();
        dept = in.readString();
        AuthEmpName = in.readString();
        FromDate = in.readString();
        ToDate = in.readString();
        NoOfDays = in.readString();
        Remarks = in.readString();
        CreatedOn = in.readString();
        AuthPerson = in.readString();
        ActiveDesc = in.readString();
        ReqAuthDesc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ReqNo);
        dest.writeString(ReqType);
        dest.writeString(ReqTypeDesc);
        dest.writeString(EmpNo);
        dest.writeString(empname);
        dest.writeString(dept);
        dest.writeString(AuthEmpName);
        dest.writeString(FromDate);
        dest.writeString(ToDate);
        dest.writeString(NoOfDays);
        dest.writeString(Remarks);
        dest.writeString(CreatedOn);
        dest.writeString(AuthPerson);
        dest.writeString(ActiveDesc);
        dest.writeString(ReqAuthDesc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LeaveRegularizationModel> CREATOR = new Creator<LeaveRegularizationModel>() {
        @Override
        public LeaveRegularizationModel createFromParcel(Parcel in) {
            return new LeaveRegularizationModel(in);
        }

        @Override
        public LeaveRegularizationModel[] newArray(int size) {
            return new LeaveRegularizationModel[size];
        }
    };

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
