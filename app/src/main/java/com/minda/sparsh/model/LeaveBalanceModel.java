package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class LeaveBalanceModel {
    @SerializedName("LeaveType")
    String LeaveType;
    @SerializedName("Unit")
    String Unit;
    @SerializedName("OpeningBalance")
    Long OpeningBalance;
    @SerializedName("Earned")
    Long Earned;
    @SerializedName("TotalEligible")
    Long TotalEligible;
    @SerializedName("Availed")
    Long Availed;
    @SerializedName("Planned")
    Long Planned;
    @SerializedName("Encashed")
    Long Encashed;
    @SerializedName("AvailableBalance")
    Long AvailableBalance;
    @SerializedName("curryr_year")
    Long curryr_year;
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

    public Long getOpeningBalance() {
        return OpeningBalance;
    }

    public void setOpeningBalance(Long openingBalance) {
        OpeningBalance = openingBalance;
    }

    public Long getEarned() {
        return Earned;
    }

    public void setEarned(Long earned) {
        Earned = earned;
    }

    public Long getTotalEligible() {
        return TotalEligible;
    }

    public void setTotalEligible(Long totalEligible) {
        TotalEligible = totalEligible;
    }

    public Long getAvailed() {
        return Availed;
    }

    public void setAvailed(Long availed) {
        Availed = availed;
    }

    public Long getPlanned() {
        return Planned;
    }

    public void setPlanned(Long planned) {
        Planned = planned;
    }

    public Long getEncashed() {
        return Encashed;
    }

    public void setEncashed(Long encashed) {
        Encashed = encashed;
    }

    public Long getAvailableBalance() {
        return AvailableBalance;
    }

    public void setAvailableBalance(Long availableBalance) {
        AvailableBalance = availableBalance;
    }

    public Long getCurryr_year() {
        return curryr_year;
    }

    public void setCurryr_year(Long curryr_year) {
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
