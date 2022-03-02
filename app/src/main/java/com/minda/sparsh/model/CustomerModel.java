package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerModel {
    @SerializedName("Data")
    List<CustomerData> data;

    public List<CustomerData> getData() {
        return data;
    }

    public void setData(List<CustomerData> data) {
        this.data = data;
    }


    public class CustomerData{
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
    }
}
