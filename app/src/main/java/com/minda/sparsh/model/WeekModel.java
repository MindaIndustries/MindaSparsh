package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class WeekModel {
    @SerializedName("Data")
    List<WeekData> Data;

    public List<WeekData> getData() {
        return Data;
    }

    public void setData(List<WeekData> data) {
        Data = data;
    }


    public static class WeekData{
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WeekData weekData = (WeekData) o;
            return Objects.equals(Weeks, weekData.Weeks) ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Weeks, Id, Name, CName);
        }
    }
}
