package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

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
}
