package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeekModel {
    @SerializedName("Data")
    List<WeekData> Data;

    public List<WeekData> getData() {
        return Data;
    }

    public void setData(List<WeekData> data) {
        Data = data;
    }


    public class WeekData{
        @SerializedName("Weeks")
        String Weeks;
        @SerializedName("Id")
        String Id;
        @SerializedName("Name")
        String Name;
        @SerializedName("CName")
        String CName;

        public String getWeeks() {
            return Weeks;
        }

        public void setWeeks(String weeks) {
            Weeks = weeks;
        }

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

        public String getCName() {
            return CName;
        }

        public void setCName(String CName) {
            this.CName = CName;
        }
    }
}
