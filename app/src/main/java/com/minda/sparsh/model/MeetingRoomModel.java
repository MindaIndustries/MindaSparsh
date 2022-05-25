package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingRoomModel {
    @SerializedName("data")
    List<MeetingRoomModelData> meetingRoomModelData;

    public List<MeetingRoomModelData> getMeetingRoomModelData() {
        return meetingRoomModelData;
    }

    public void setMeetingRoomModelData(List<MeetingRoomModelData> meetingRoomModelData) {
        this.meetingRoomModelData = meetingRoomModelData;
    }

    public class MeetingRoomModelData{
        @SerializedName("UnitsID")
        int UnitsID;
        @SerializedName("UnitCode")
        String UnitCode;
        @SerializedName("UnitName")
        String UnitName;
        @SerializedName("Active")
        boolean Active;


        public int getUnitsID() {
            return UnitsID;
        }

        public void setUnitsID(int unitsID) {
            UnitsID = unitsID;
        }

        public String getUnitCode() {
            return UnitCode;
        }

        public void setUnitCode(String unitCode) {
            UnitCode = unitCode;
        }

        public String getUnitName() {
            return UnitName;
        }

        public void setUnitName(String unitName) {
            UnitName = unitName;
        }

        public boolean isActive() {
            return Active;
        }

        public void setActive(boolean active) {
            Active = active;
        }
    }
}
