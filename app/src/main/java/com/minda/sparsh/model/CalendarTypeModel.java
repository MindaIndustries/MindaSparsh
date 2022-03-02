package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalendarTypeModel {
    @SerializedName("Data")
    List<CalendarTypeData> data;

    public List<CalendarTypeData> getData() {
        return data;
    }

    public void setData(List<CalendarTypeData> data) {
        this.data = data;
    }

    public class CalendarTypeData{
        @SerializedName("Name")
        String Name;
        @SerializedName("Id")
        String Id;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }
    }
}
