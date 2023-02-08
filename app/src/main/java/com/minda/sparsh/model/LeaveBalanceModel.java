package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveBalanceModel {
    @SerializedName("LeaveType")
    String LeaveType;
    @SerializedName("Unit")
    String Unit;
    @SerializedName("OpeningBalance")
    double OpeningBalance;
    @SerializedName("Earned")
    double Earned;
    @SerializedName("TotalEligible")
    double TotalEligible;
    @SerializedName("Availed")
    double Availed;
    @SerializedName("Planned")
    double Planned;
    @SerializedName("Encashed")
    double Encashed;
    @SerializedName("AvailableBalance")
    double AvailableBalance;
    @SerializedName("curryr_year")
    double curryr_year;
    @SerializedName("LTA_Balance")
    int LTA_Balance;
    @SerializedName("Balance")
    double Balance;


    public String getLeaveType() {
        return LeaveType;
    }

    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public double getOpeningBalance() {
        return OpeningBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        OpeningBalance = openingBalance;
    }

    public double getEarned() {
        return Earned;
    }

    public void setEarned(double earned) {
        Earned = earned;
    }

    public double getTotalEligible() {
        return TotalEligible;
    }

    public void setTotalEligible(double totalEligible) {
        TotalEligible = totalEligible;
    }

    public double getAvailed() {
        return Availed;
    }

    public void setAvailed(double availed) {
        Availed = availed;
    }

    public double getPlanned() {
        return Planned;
    }

    public void setPlanned(double planned) {
        Planned = planned;
    }

    public double getEncashed() {
        return Encashed;
    }

    public void setEncashed(double encashed) {
        Encashed = encashed;
    }

    public double getAvailableBalance() {
        return AvailableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        AvailableBalance = availableBalance;
    }

    public double getCurryr_year() {
        return curryr_year;
    }

    public void setCurryr_year(double curryr_year) {
        this.curryr_year = curryr_year;
    }

    public int getLTA_Balance() {
        return LTA_Balance;
    }

    public void setLTA_Balance(int LTA_Balance) {
        this.LTA_Balance = LTA_Balance;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }
}
