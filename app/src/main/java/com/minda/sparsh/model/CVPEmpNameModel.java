package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CVPEmpNameModel {
    @SerializedName("Data")
    List<CVPEmpNameData> cvpEmpNameData;

    public List<CVPEmpNameData> getCvpEmpNameData() {
        return cvpEmpNameData;
    }

    public void setCvpEmpNameData(List<CVPEmpNameData> cvpEmpNameData) {
        this.cvpEmpNameData = cvpEmpNameData;
    }


    public class CVPEmpNameData{
        @SerializedName("Name")
        String Name;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}
