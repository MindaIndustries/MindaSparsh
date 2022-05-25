package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingRoomListModel {
    @SerializedName("data")
    List<MeetingRoomListModelData> meetingRoomListModelData;

    public List<MeetingRoomListModelData> getMeetingRoomListModelData() {
        return meetingRoomListModelData;
    }

    public void setMeetingRoomListModelData(List<MeetingRoomListModelData> meetingRoomListModelData) {
        this.meetingRoomListModelData = meetingRoomListModelData;
    }


    public class MeetingRoomListModelData{
        @SerializedName("MeetingRoomLnkID")
        int MeetingRoomLnkID;
        @SerializedName("MeetingRoomID")
        int MeetingRoomID;
        @SerializedName("Status")
        boolean Status;
        @SerializedName("MeetingRoomID1")
        int MeetingRoomID1;
        @SerializedName("MeetingRoom")
        String MeetingRoom;
        @SerializedName("Status1")
        boolean Status1;
        @SerializedName("NoOfSeats")
        int NoOfSeats;
        @SerializedName("Seats")
        boolean Seats;
        @SerializedName("AC")
        boolean AC;
        @SerializedName("WiFi")
        boolean WiFi;
        @SerializedName("MIC")
        boolean MIC;
        @SerializedName("WhiteBoard")
        boolean WhiteBoard;
        @SerializedName("Projecter")
        boolean Projecter;
        @SerializedName("VideoConference")
        boolean VideoConference;
        @SerializedName("PanaBoard")
        boolean PanaBoard;
        @SerializedName("UnitIDS")
        String UnitIDS;
        @SerializedName("ACText")
        String ACText;
        @SerializedName("WiFiText")
        String WiFiText;
        @SerializedName("MICText")
        String MICText;
        @SerializedName("WhiteBoardText")
        String WhiteBoardText;
        @SerializedName("ProjectorText")
        String ProjectorText;
        @SerializedName("VCText")
        String VCText;
        @SerializedName("PanaText")
        String PanaText;


        public int getMeetingRoomLnkID() {
            return MeetingRoomLnkID;
        }

        public void setMeetingRoomLnkID(int meetingRoomLnkID) {
            MeetingRoomLnkID = meetingRoomLnkID;
        }

        public int getMeetingRoomID() {
            return MeetingRoomID;
        }

        public void setMeetingRoomID(int meetingRoomID) {
            MeetingRoomID = meetingRoomID;
        }

        public boolean isStatus() {
            return Status;
        }

        public void setStatus(boolean status) {
            Status = status;
        }

        public int getMeetingRoomID1() {
            return MeetingRoomID1;
        }

        public void setMeetingRoomID1(int meetingRoomID1) {
            MeetingRoomID1 = meetingRoomID1;
        }

        public String getMeetingRoom() {
            return MeetingRoom;
        }

        public void setMeetingRoom(String meetingRoom) {
            MeetingRoom = meetingRoom;
        }

        public boolean isStatus1() {
            return Status1;
        }

        public void setStatus1(boolean status1) {
            Status1 = status1;
        }

        public int getNoOfSeats() {
            return NoOfSeats;
        }

        public void setNoOfSeats(int noOfSeats) {
            NoOfSeats = noOfSeats;
        }

        public boolean isSeats() {
            return Seats;
        }

        public void setSeats(boolean seats) {
            Seats = seats;
        }

        public boolean isAC() {
            return AC;
        }

        public void setAC(boolean AC) {
            this.AC = AC;
        }

        public boolean isWiFi() {
            return WiFi;
        }

        public void setWiFi(boolean wiFi) {
            WiFi = wiFi;
        }

        public boolean isMIC() {
            return MIC;
        }

        public void setMIC(boolean MIC) {
            this.MIC = MIC;
        }

        public boolean isWhiteBoard() {
            return WhiteBoard;
        }

        public void setWhiteBoard(boolean whiteBoard) {
            WhiteBoard = whiteBoard;
        }

        public boolean isProjecter() {
            return Projecter;
        }

        public void setProjecter(boolean projecter) {
            Projecter = projecter;
        }

        public boolean isVideoConference() {
            return VideoConference;
        }

        public void setVideoConference(boolean videoConference) {
            VideoConference = videoConference;
        }

        public boolean isPanaBoard() {
            return PanaBoard;
        }

        public void setPanaBoard(boolean panaBoard) {
            PanaBoard = panaBoard;
        }

        public String getUnitIDS() {
            return UnitIDS;
        }

        public void setUnitIDS(String unitIDS) {
            UnitIDS = unitIDS;
        }

        public String getACText() {
            return ACText;
        }

        public void setACText(String ACText) {
            this.ACText = ACText;
        }

        public String getWiFiText() {
            return WiFiText;
        }

        public void setWiFiText(String wiFiText) {
            WiFiText = wiFiText;
        }

        public String getMICText() {
            return MICText;
        }

        public void setMICText(String MICText) {
            this.MICText = MICText;
        }

        public String getWhiteBoardText() {
            return WhiteBoardText;
        }

        public void setWhiteBoardText(String whiteBoardText) {
            WhiteBoardText = whiteBoardText;
        }

        public String getProjectorText() {
            return ProjectorText;
        }

        public void setProjectorText(String projectorText) {
            ProjectorText = projectorText;
        }

        public String getVCText() {
            return VCText;
        }

        public void setVCText(String VCText) {
            this.VCText = VCText;
        }

        public String getPanaText() {
            return PanaText;
        }

        public void setPanaText(String panaText) {
            PanaText = panaText;
        }
    }
}
