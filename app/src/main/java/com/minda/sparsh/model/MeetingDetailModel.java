package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.text.AttributedString;
import java.util.List;

public class MeetingDetailModel {
    @SerializedName("data")
    List<MeetingDetailModelData> data;

    public List<MeetingDetailModelData> getData() {
        return data;
    }

    public void setData(List<MeetingDetailModelData> data) {
        this.data = data;
    }

    public class MeetingDetailModelData{
        @SerializedName("MeetingID")
        String MeetingID;
        @SerializedName("TimeSlot")
        String TimeSlot;
        @SerializedName("EmpCode")
        String EmpCode;
        @SerializedName("BookingDate")
        String BookingDate;
        @SerializedName("AttendeeExt")
        String AttendeeExt;
        @SerializedName("AttendeeInt")
        String AttendeeInt;
        @SerializedName("MeetingRoomSlotID")
        String MeetingRoomSlotID;
        @SerializedName("UM_USER_DESC")
        String UM_USER_DESC;
        @SerializedName("MeetingRoom")
        String MeetingRoom;
        @SerializedName("Purpose")
        String Purpose;
        @SerializedName("DivID")
        String DivID;
        @SerializedName("Status")
        int Status;
        @SerializedName("BkngStatus")
        String BkngStatus;
        @SerializedName("AttendeeInternal")
        String AttendeeInternal;

        public String getMeetingID() {
            return MeetingID;
        }

        public void setMeetingID(String meetingID) {
            MeetingID = meetingID;
        }

        public String getTimeSlot() {
            return TimeSlot;
        }

        public void setTimeSlot(String timeSlot) {
            TimeSlot = timeSlot;
        }

        public String getEmpCode() {
            return EmpCode;
        }

        public void setEmpCode(String empCode) {
            EmpCode = empCode;
        }

        public String getBookingDate() {
            return BookingDate;
        }

        public void setBookingDate(String bookingDate) {
            BookingDate = bookingDate;
        }

        public String getAttendeeExt() {
            return AttendeeExt;
        }

        public void setAttendeeExt(String attendeeExt) {
            AttendeeExt = attendeeExt;
        }

        public String getAttendeeInt() {
            return AttendeeInt;
        }

        public void setAttendeeInt(String attendeeInt) {
            AttendeeInt = attendeeInt;
        }

        public String getMeetingRoomSlotID() {
            return MeetingRoomSlotID;
        }

        public void setMeetingRoomSlotID(String meetingRoomSlotID) {
            MeetingRoomSlotID = meetingRoomSlotID;
        }

        public String getUM_USER_DESC() {
            return UM_USER_DESC;
        }

        public void setUM_USER_DESC(String UM_USER_DESC) {
            this.UM_USER_DESC = UM_USER_DESC;
        }

        public String getMeetingRoom() {
            return MeetingRoom;
        }

        public void setMeetingRoom(String meetingRoom) {
            MeetingRoom = meetingRoom;
        }

        public String getPurpose() {
            return Purpose;
        }

        public void setPurpose(String purpose) {
            Purpose = purpose;
        }

        public String getDivID() {
            return DivID;
        }

        public void setDivID(String divID) {
            DivID = divID;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public String getBkngStatus() {
            return BkngStatus;
        }

        public void setBkngStatus(String bkngStatus) {
            BkngStatus = bkngStatus;
        }

        public String getAttendeeInternal() {
            return AttendeeInternal;
        }

        public void setAttendeeInternal(String attendeeInternal) {
            AttendeeInternal = attendeeInternal;
        }
    }
}
