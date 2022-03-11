package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class CustomerModel {
    @SerializedName("Data")
    List<CustomerData> data;

    public List<CustomerData> getData() {
        return data;
    }

    public void setData(List<CustomerData> data) {
        this.data = data;
    }


    public static class CustomerData{
        @SerializedName("Name")
        String Name;
        @SerializedName("ID")
        String id;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CustomerData that = (CustomerData) o;
            return  Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Name, id);
        }
    }
}
