package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class CalendarTypeModel {
    @SerializedName("Data")
    List<CalendarTypeData> data;

    public List<CalendarTypeData> getData() {
        return data;
    }

    public void setData(List<CalendarTypeData> data) {
        this.data = data;
    }

    public static class CalendarTypeData{
        @SerializedName("Name")
        String Name;
        @SerializedName("Id")
        String Id;

        public CalendarTypeData(String id) {
            Id = id;
        }

        public CalendarTypeData() {
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CalendarTypeData that = (CalendarTypeData) o;
            return Objects.equals(Id, that.Id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Name, Id);
        }
    }
}
