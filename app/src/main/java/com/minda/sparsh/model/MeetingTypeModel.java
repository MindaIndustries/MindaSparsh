package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingTypeModel {
    @SerializedName("Data")
    List<MeetingTypeData> data;

    public List<MeetingTypeData> getData() {
        return data;
    }

    public void setData(List<MeetingTypeData> data) {
        this.data = data;
    }

    public class MeetingTypeData{
        @SerializedName("Id")
        String Id;
        @SerializedName("Name")
        String Name;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}
