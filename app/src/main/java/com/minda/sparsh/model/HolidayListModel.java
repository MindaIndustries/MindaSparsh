package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class HolidayListModel {

    @SerializedName("hol_date")
    String hol_date;
    @SerializedName("Date")
    String Date;
    @SerializedName("Desc")
    String Desc;
    @SerializedName("Day")
    String Day;

    public String getHol_date() {
        return hol_date;
    }

    public void setHol_date(String hol_date) {
        this.hol_date = hol_date;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }
}
