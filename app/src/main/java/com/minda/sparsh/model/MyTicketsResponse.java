package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class MyTicketsResponse implements Serializable {

    @SerializedName("ID")
    String ID;
    @SerializedName("TicketNo")
    String TicketNo;
    @SerializedName("TicketType")
    String TicketType;
    @SerializedName("closerdate")
    String closerdate;
    @SerializedName("status")
    String status;
    @SerializedName("Description")
    String Description;
    @SerializedName("reporteddate")
    String reporteddate;
    @SerializedName("assigne")
    String assigne;
    @SerializedName("remark")
    String remark;
    @SerializedName("location")
    String location;
    @SerializedName("reportedby")
    String reportedby;
    @SerializedName("Files")
    String Files;
    @SerializedName("Files2")
    String Files2;
    @SerializedName("createdon")
    String createdon;
    @SerializedName("Description1")
    String Description1;
    @SerializedName("CC")
    String CC;
    @SerializedName("UnitCode")
    String UnitCode;
    @SerializedName("TicketGroup")
    String TicketGroup;
    @SerializedName("SubCat")
    String SubCat;
    @SerializedName("SubCat2")
    String SubCat2;
    @SerializedName("SubCat3")
    String SubCat3;
    @SerializedName("Priority")
    String Priority;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTicketNo() {
        return TicketNo;
    }

    public void setTicketNo(String ticketNo) {
        TicketNo = ticketNo;
    }

    public String getTicketType() {
        return TicketType;
    }

    public void setTicketType(String ticketType) {
        TicketType = ticketType;
    }

    public String getCloserdate() {
        return closerdate;
    }

    public void setCloserdate(String closerdate) {
        this.closerdate = closerdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getReporteddate() {
        return reporteddate;
    }

    public void setReporteddate(String reporteddate) {
        this.reporteddate = reporteddate;
    }

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReportedby() {
        return reportedby;
    }

    public void setReportedby(String reportedby) {
        this.reportedby = reportedby;
    }

    public String getFiles() {
        return Files;
    }

    public void setFiles(String files) {
        Files = files;
    }

    public String getFiles2() {
        return Files2;
    }

    public void setFiles2(String files2) {
        Files2 = files2;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getDescription1() {
        return Description1;
    }

    public void setDescription1(String description1) {
        Description1 = description1;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        UnitCode = unitCode;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MyTicketsResponse)) return false;

        MyTicketsResponse that = (MyTicketsResponse) obj;

        if ((location != "0" && !location.equals(that.location)))
            return false;


        return true;
    }

    public String getTicketGroup() {
        return TicketGroup;
    }

    public void setTicketGroup(String ticketGroup) {
        TicketGroup = ticketGroup;
    }

    public String getSubCat() {
        return SubCat;
    }

    public void setSubCat(String subCat) {
        SubCat = subCat;
    }

    public String getSubCat2() {
        return SubCat2;
    }

    public void setSubCat2(String subCat2) {
        SubCat2 = subCat2;
    }

    public String getSubCat3() {
        return SubCat3;
    }

    public void setSubCat3(String subCat3) {
        SubCat3 = subCat3;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }
}
