package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class LocationModel {
    @SerializedName("Data")
    List<LocationData> data;

    public List<LocationData> getData() {
        return data;
    }

    public void setData(List<LocationData> data) {
        this.data = data;
    }


    public static class LocationData{
        @SerializedName("ID")
        String ID;
        @SerializedName("location")
        String location;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LocationData that = (LocationData) o;
            return Objects.equals(ID, that.ID);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ID, location);
        }
    }
}
