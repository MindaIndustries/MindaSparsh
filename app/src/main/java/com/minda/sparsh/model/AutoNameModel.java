package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class AutoNameModel {
    @SerializedName("data")
    List<AutoNameModelData> data;

    public List<AutoNameModelData> getData() {
        return data;
    }

    public void setData(List<AutoNameModelData> data) {
        this.data = data;
    }


    public static class AutoNameModelData{
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AutoNameModelData that = (AutoNameModelData) o;
            return Objects.equals(Value, that.Value) && Objects.equals(EmpCode, that.EmpCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Value, EmpCode);
        }
    }
}
