package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutoNameModel {
    @SerializedName("data")
    List<AutoNameModelData> data;

    public List<AutoNameModelData> getData() {
        return data;
    }

    public void setData(List<AutoNameModelData> data) {
        this.data = data;
    }


    public class AutoNameModelData{
        @SerializedName("Value")
        String Value;
        @SerializedName("EmpCode")
        String EmpCode;

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        public String getEmpCode() {
            return EmpCode;
        }

        public void setEmpCode(String empCode) {
            EmpCode = empCode;
        }
    }
}
