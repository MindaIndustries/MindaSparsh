package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class ApprovalRequestModel {
    @SerializedName("Reqno")
    String Reqno;
    @SerializedName("ReqType")
    String ReqType;
    @SerializedName("ReqTypeDesc")
    String ReqTypeDesc;
    @SerializedName("EmpNo")
    String EmpNo;
    @SerializedName("empname")
    String empname;
    @SerializedName("lve_availed")
    double lve_availed;
    @SerializedName("FromDate")
    String FromDate;
    @SerializedName("ToDate")
    String ToDate;
    @SerializedName("NoOfDays")
    double NoOfDays;
    @SerializedName("ReasonDesc")
    String ReasonDesc;
    @SerializedName("Remarks")
    String Remarks;
    @SerializedName("FileInfo")
    String FileInfo;

    public String getReqno() {
        return Reqno;
    }

    public void setReqno(String reqno) {
        Reqno = reqno;
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

    public double getLve_availed() {
        return lve_availed;
    }

    public void setLve_availed(double lve_availed) {
        this.lve_availed = lve_availed;
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

    public double getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(double noOfDays) {
        NoOfDays = noOfDays;
    }

    public String getReasonDesc() {
        return ReasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        ReasonDesc = reasonDesc;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getFileInfo() {
        return FileInfo;
    }

    public void setFileInfo(String fileInfo) {
        FileInfo = fileInfo;
    }
}
