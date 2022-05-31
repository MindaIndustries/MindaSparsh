package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingRoomBookData {
    @SerializedName("data")
    List<MeetingRoomBookDataModel> data;

    public List<MeetingRoomBookDataModel> getData() {
        return data;
    }

    public void setData(List<MeetingRoomBookDataModel> data) {
        this.data = data;
    }


    public class MeetingRoomBookDataModel{
        @SerializedName("MeetingID")
        String MeetingID;
        @SerializedName("CreatedDate")
        String CreatedDate;
        @SerializedName("AttendeeInt")
        String AttendeeInt;
        @SerializedName("UnitName")
        String UnitName;
        @SerializedName("AttendeeExt")
        String AttendeeExt;
        @SerializedName("MeetingRoom")
        String MeetingRoom;
        @SerializedName("Purpose")
        String Purpose;
        @SerializedName("BkngStatus")
        String BkngStatus;

        public String getMeetingID() {
            return MeetingID;
        }

        public void setMeetingID(String meetingID) {
            MeetingID = meetingID;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String createdDate) {
            CreatedDate = createdDate;
        }

        public String getAttendeeInt() {
            return AttendeeInt;
        }

        public void setAttendeeInt(String attendeeInt) {
            AttendeeInt = attendeeInt;
        }

        public String getUnitName() {
            return UnitName;
        }

        public void setUnitName(String unitName) {
            UnitName = unitName;
        }

        public String getAttendeeExt() {
            return AttendeeExt;
        }

        public void setAttendeeExt(String attendeeExt) {
            AttendeeExt = attendeeExt;
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

        public String getBkngStatus() {
            return BkngStatus;
        }

        public void setBkngStatus(String bkngStatus) {
            BkngStatus = bkngStatus;
        }
    }
}
