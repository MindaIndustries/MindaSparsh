package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuesResponse {
    @SerializedName("ID")
    String ID;
    @SerializedName("Ques")
    String Ques;
    @SerializedName("Active")
    String Active;
    @SerializedName("Types")
    String Types;
    @SerializedName("Opts")
    String Opts;
    @SerializedName("Address")
    String Address;
    @SerializedName("Age")
    String Age;
    @SerializedName("City")
    String City;
    @SerializedName("Gender")
    String Gender;
    @SerializedName("MobileNo")
    String MobileNo;
    @SerializedName("State")
    String State;
    @SerializedName("EmpName")
    String EmpName;
    @SerializedName("Unit")
    String Unit;
    @SerializedName("EmpCode")
    String EmpCode;
    ArrayList<String> OptionsList;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getQues() {
        return Ques;
    }

    public void setQues(String ques) {
        Ques = ques;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getTypes() {
        return Types;
    }

    public void setTypes(String types) {
        Types = types;
    }

    public String getOpts() {
        return Opts;
    }

    public void setOpts(String opts) {
        Opts = opts;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getEmpCode() {
        return EmpCode;
    }

    public void setEmpCode(String empCode) {
        EmpCode = empCode;
    }

    public ArrayList<String> getOptionsList() {
        return OptionsList;
    }

    public void setOptionsList(ArrayList<String> optionsList) {
        OptionsList = optionsList;
    }
}
