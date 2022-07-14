package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class MeetingRoomDetailData {
    @SerializedName("data")
    List<MeetingRoomDetailDataModel> data;

    public List<MeetingRoomDetailDataModel> getData() {
        return data;
    }

    public void setData(List<MeetingRoomDetailDataModel> data) {
        this.data = data;
    }

    public static class MeetingRoomDetailDataModel{
        @SerializedName("TimeStart")
        String TimeStart;
        @SerializedName("TimeEnd")
        String TimeEnd;
        @SerializedName("TimeSlotID")
        int TimeSlotID;
        @SerializedName("MeetingTimeLnkID")
        int MeetingTimeLnkID;
        @SerializedName("Status")
        boolean Status;
        @SerializedName("BkngStatus")
        String BkngStatus;
        @SerializedName("MeetingRoomID")
        int MeetingRoomID;


        public String getTimeStart() {
            return TimeStart;
        }

        public void setTimeStart(String timeStart) {
            TimeStart = timeStart;
        }

        public String getTimeEnd() {
            return TimeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            TimeEnd = timeEnd;
        }

        public int getTimeSlotID() {
            return TimeSlotID;
        }

        public void setTimeSlotID(int timeSlotID) {
            TimeSlotID = timeSlotID;
        }

        public int getMeetingTimeLnkID() {
            return MeetingTimeLnkID;
        }

        public void setMeetingTimeLnkID(int meetingTimeLnkID) {
            MeetingTimeLnkID = meetingTimeLnkID;
        }

        public boolean isStatus() {
            return Status;
        }

        public void setStatus(boolean status) {
            Status = status;
        }

        public String getBkngStatus() {
            return BkngStatus;
        }

        public void setBkngStatus(String bkngStatus) {
            BkngStatus = bkngStatus;
        }

        public int getMeetingRoomID() {
            return MeetingRoomID;
        }

        public void setMeetingRoomID(int meetingRoomID) {
            MeetingRoomID = meetingRoomID;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MeetingRoomDetailDataModel)) return false;
            MeetingRoomDetailDataModel that = (MeetingRoomDetailDataModel) o;
            return getMeetingTimeLnkID() == that.getMeetingTimeLnkID();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTimeSlotID());
        }
    }
}
