package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DWMPlantModel {
    @SerializedName("CATID")
    Integer CATID;
    @SerializedName("CatName")
    String CatName;
    @SerializedName("SubCategory")
    List<SubCategory> SubCategory;
    public Integer getCATID() {
        return CATID;
    }

    public void setCATID(Integer CATID) {
        this.CATID = CATID;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public List<DWMPlantModel.SubCategory> getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(List<DWMPlantModel.SubCategory> subCategory) {
        SubCategory = subCategory;
    }




    public  class  SubCategory{
        @SerializedName("CATID")
        Integer CATID;
        @SerializedName("SubCATID")
        Integer SubCATID;
        @SerializedName("SubCat")
        String SubCat;
        @SerializedName("Feq")
        String Feq;
        @SerializedName("Hrs")
        String Hrs;
        @SerializedName("Q")
        String Q;
        @SerializedName("HY")
        String HY;
        @SerializedName("Totaltime")
        String Totaltime;
        @SerializedName("Val")
        String Val;
        @SerializedName("Remark")
        String Remark;
        @SerializedName("Date")
        String Date;
        @SerializedName("SubSubCatName")
        String SubSubCatName;
        @SerializedName("Sno")
        String Sno;
        @SerializedName("type")
        int type;



        public Integer getCATID() {
            return CATID;
        }

        public void setCATID(Integer CATID) {
            this.CATID = CATID;
        }

        public Integer getSubCATID() {
            return SubCATID;
        }

        public void setSubCATID(Integer subCATID) {
            SubCATID = subCATID;
        }

        public String getSubCat() {
            return SubCat;
        }

        public void setSubCat(String subCat) {
            SubCat = subCat;
        }

        public String getFeq() {
            return Feq;
        }

        public void setFeq(String feq) {
            Feq = feq;
        }

        public String getHrs() {
            return Hrs;
        }

        public void setHrs(String hrs) {
            Hrs = hrs;
        }

        public String getQ() {
            return Q;
        }

        public void setQ(String q) {
            Q = q;
        }

        public String getHY() {
            return HY;
        }

        public void setHY(String HY) {
            this.HY = HY;
        }

        public String getTotaltime() {
            return Totaltime;
        }

        public void setTotaltime(String totaltime) {
            Totaltime = totaltime;
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

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getSubSubCatName() {
            return SubSubCatName;
        }

        public void setSubSubCatName(String subSubCatName) {
            SubSubCatName = subSubCatName;
        }

        public String getSno() {
            return Sno;
        }

        public void setSno(String sno) {
            Sno = sno;
        }
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
