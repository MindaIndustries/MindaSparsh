package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class UpdateGuideLine {
    @SerializedName("Column1")
     String Column1;

    public String getColumn1() {
        return Column1;
    }

    public void setColumn1(String column1) {
        Column1 = column1;
    }
}
