package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityModel {
    @SerializedName("data")
    List<City> data;

    public List<City> getData() {
        return data;
    }

    public void setData(List<City> data) {
        this.data = data;
    }


    public class City {
    @SerializedName("City")
    String City;
    @SerializedName("Units")
    String Units;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }
}
}

