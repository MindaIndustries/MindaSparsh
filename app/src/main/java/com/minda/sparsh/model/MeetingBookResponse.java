package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class MeetingBookResponse {
    @SerializedName("data")
    String data;
    @SerializedName("MeetingID")
    String MeetingId;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMeetingId() {
        return MeetingId;
    }

    public void setMeetingId(String meetingId) {
        MeetingId = meetingId;
    }
}
