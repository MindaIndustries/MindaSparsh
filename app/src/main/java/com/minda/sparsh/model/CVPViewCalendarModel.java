package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;
import com.minda.sparsh.cvp.CVPViewCalendar;

import java.util.List;

public class CVPViewCalendarModel {
    @SerializedName("Data")
    List<CVPViewCalendarData> data;

    public List<CVPViewCalendarData> getData() {
        return data;
    }

    public void setData(List<CVPViewCalendarData> data) {
        this.data = data;
    }

    public class CVPViewCalendarData{
        @SerializedName("RangeDate")
        String RangeDate;
        @SerializedName("monthc")
        String monthc;
        @SerializedName("Attcss")
        String Attcss;
        @SerializedName("ID")
        int ID;
        @SerializedName("MID")
        int MID;
        @SerializedName("weeks")
        String weeks;
        @SerializedName("CustomerName")
        String CustomerName;
        @SerializedName("MeetingType")
        String MeetingType;
        @SerializedName("Name")
        String Name;
        @SerializedName("CName")
        String  CName;
        @SerializedName("IsEdit")
        int IsEdit;
        @SerializedName("IsDelete")
        int IsDelete;

        public String getRangeDate() {
            return RangeDate;
        }

        public void setRangeDate(String rangeDate) {
            RangeDate = rangeDate;
        }

        public String getMonthc() {
            return monthc;
        }

        public void setMonthc(String monthc) {
            this.monthc = monthc;
        }

        public String getAttcss() {
            return Attcss;
        }

        public void setAttcss(String attcss) {
            Attcss = attcss;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getMID() {
            return MID;
        }

        public void setMID(int MID) {
            this.MID = MID;
        }

        public String getWeeks() {
            return weeks;
        }

        public void setWeeks(String weeks) {
            this.weeks = weeks;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public String getMeetingType() {
            return MeetingType;
        }

        public void setMeetingType(String meetingType) {
            MeetingType = meetingType;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getCName() {
            return CName;
        }

        public void setCName(String CName) {
            this.CName = CName;
        }

        public int getIsEdit() {
            return IsEdit;
        }

        public void setIsEdit(int isEdit) {
            IsEdit = isEdit;
        }

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int isDelete) {
            IsDelete = isDelete;
        }
    }
}
