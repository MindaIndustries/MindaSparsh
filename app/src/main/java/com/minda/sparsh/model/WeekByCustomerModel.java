package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeekByCustomerModel {
    @SerializedName("Data")
    List<WeekByCustomerData> data;

    public List<WeekByCustomerData> getData() {
        return data;
    }

    public void setData(List<WeekByCustomerData> data) {
        this.data = data;
    }

    public class WeekByCustomerData{
        @SerializedName("id")
        String id;
        @SerializedName("Weeks")
        String Weeks;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWeeks() {
            return Weeks;
        }

        public void setWeeks(String weeks) {
            Weeks = weeks;
        }
    }
}
