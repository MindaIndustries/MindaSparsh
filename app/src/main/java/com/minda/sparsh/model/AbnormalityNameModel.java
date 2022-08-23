package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbnormalityNameModel {

    @SerializedName("data")
    List<NameEmpcode> list;

    public List<NameEmpcode> getList() {
        return list;
    }

    public void setList(List<NameEmpcode> list) {
        this.list = list;
    }


    public class NameEmpcode{
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
