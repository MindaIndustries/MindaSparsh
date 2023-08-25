package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DWMPostModel {
    @SerializedName("EmpCode")
    String EmpCode;
    @SerializedName("Date")
    String Date;
    @SerializedName("Type")
    String Type;
    @SerializedName("SaveList")
    List<SaveList> saveList;

    public String getEmpCode() {
        return EmpCode;
    }

    public void setEmpCode(String empCode) {
        EmpCode = empCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public List<SaveList> getSaveList() {
        return saveList;
    }

    public void setSaveList(List<SaveList> saveList) {
        this.saveList = saveList;
    }

    public class SaveList{
        @SerializedName("CatID")
        String CatID;
        @SerializedName("SubCatID")
        String SubCatID;
        @SerializedName("Val")
        String Val;
        @SerializedName("Remark")
        String Remark;

        public String getCatID() {
            return CatID;
        }

        public void setCatID(String catID) {
            CatID = catID;
        }

        public String getSubCatID() {
            return SubCatID;
        }

        public void setSubCatID(String subCatID) {
            SubCatID = subCatID;
        }

        public String getVal() {
            return Val;
        }

        public void setVal(String val) {
            Val = val;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }
    }

}
