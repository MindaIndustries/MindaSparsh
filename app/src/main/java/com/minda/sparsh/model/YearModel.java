package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YearModel {
    @SerializedName("Data")
    List<YearModelData> data;

    public List<YearModelData> getData() {
        return data;
    }

    public void setData(List<YearModelData> data) {
        this.data = data;
    }

    public class YearModelData{
        @SerializedName("year")
        String year;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
