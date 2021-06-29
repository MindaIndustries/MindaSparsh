package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class AboutUsDetails {
    @SerializedName("Id")
    String Id;
    @SerializedName("FY")
    String FY;
    @SerializedName("JointVenture")
    String JointVenture;
    @SerializedName("GrpTrnOver")
    String GrpTrnOver;
    @SerializedName("Acquisition")
    String Acquisition;
    @SerializedName("PlantsGlobally")
    String PlantsGlobally;
    @SerializedName("RDCenter")
    String RDCenter;
    @SerializedName("ProductLine")
    String ProductLine;
    @SerializedName("ProductPatent")
    String ProductPatent;
    @SerializedName("DesignRegsistration")
    String DesignRegsistration;
    @SerializedName("Status")
    String Status;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFY() {
        return FY;
    }

    public void setFY(String FY) {
        this.FY = FY;
    }

    public String getJointVenture() {
        return JointVenture;
    }

    public void setJointVenture(String jointVenture) {
        JointVenture = jointVenture;
    }

    public String getGrpTrnOver() {
        return GrpTrnOver;
    }

    public void setGrpTrnOver(String grpTrnOver) {
        GrpTrnOver = grpTrnOver;
    }

    public String getAcquisition() {
        return Acquisition;
    }

    public void setAcquisition(String acquisition) {
        Acquisition = acquisition;
    }

    public String getPlantsGlobally() {
        return PlantsGlobally;
    }

    public void setPlantsGlobally(String plantsGlobally) {
        PlantsGlobally = plantsGlobally;
    }

    public String getRDCenter() {
        return RDCenter;
    }

    public void setRDCenter(String RDCenter) {
        this.RDCenter = RDCenter;
    }

    public String getProductLine() {
        return ProductLine;
    }

    public void setProductLine(String productLine) {
        ProductLine = productLine;
    }

    public String getProductPatent() {
        return ProductPatent;
    }

    public void setProductPatent(String productPatent) {
        ProductPatent = productPatent;
    }

    public String getDesignRegsistration() {
        return DesignRegsistration;
    }

    public void setDesignRegsistration(String designRegsistration) {
        DesignRegsistration = designRegsistration;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
