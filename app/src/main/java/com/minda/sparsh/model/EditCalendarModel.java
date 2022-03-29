package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditCalendarModel {
    @SerializedName("Data")
    List<EditCalendarModelData> data;

    public List<EditCalendarModelData> getData() {
        return data;
    }

    public void setData(List<EditCalendarModelData> data) {
        this.data = data;
    }

    public class EditCalendarModelData{
        @SerializedName("MeetingType")
        String MeetingType;
        @SerializedName("PersonName")
        String PersonName;
        @SerializedName("CustomerName")
        String CustomerName;
        @SerializedName("WeekName")
        String WeekName;
        @SerializedName("ID")
        int ID;
        @SerializedName("CustomerID")
        int CustomerID;
        @SerializedName("LocationID")
        int LocationID;
        @SerializedName("MeetingType1")
        String MeetingType1;
        @SerializedName("WeekDaysID")
        String WeekDaysID;
        @SerializedName("CalendarBookingDate")
        String CalendarBookingDate;


        public String getMeetingType() {
            return MeetingType;
        }

        public void setMeetingType(String meetingType) {
            MeetingType = meetingType;
        }

        public String getPersonName() {
            return PersonName;
        }

        public void setPersonName(String personName) {
            PersonName = personName;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public String getWeekName() {
            return WeekName;
        }

        public void setWeekName(String weekName) {
            WeekName = weekName;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(int customerID) {
            CustomerID = customerID;
        }

        public int getLocationID() {
            return LocationID;
        }

        public void setLocationID(int locationID) {
            LocationID = locationID;
        }

        public String getMeetingType1() {
            return MeetingType1;
        }

        public void setMeetingType1(String meetingType1) {
            MeetingType1 = meetingType1;
        }

        public String getWeekDaysID() {
            return WeekDaysID;
        }

        public void setWeekDaysID(String weekDaysID) {
            WeekDaysID = weekDaysID;
        }

        public String getCalendarBookingDate() {
            return CalendarBookingDate;
        }

        public void setCalendarBookingDate(String calendarBookingDate) {
            CalendarBookingDate = calendarBookingDate;
        }
    }
}
