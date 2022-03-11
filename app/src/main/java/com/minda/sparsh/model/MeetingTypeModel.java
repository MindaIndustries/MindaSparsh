package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class MeetingTypeModel {
    @SerializedName("Data")
    List<MeetingTypeData> data;

    public List<MeetingTypeData> getData() {
        return data;
    }

    public void setData(List<MeetingTypeData> data) {
        this.data = data;
    }

    public static class MeetingTypeData{
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MeetingTypeData that = (MeetingTypeData) o;
            return Objects.equals(Id, that.Id) ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Id, Name);
        }
    }
}
