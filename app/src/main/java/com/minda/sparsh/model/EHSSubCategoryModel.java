package com.minda.sparsh.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EHSSubCategoryModel {

    @SerializedName("ID")
    String ID;
    @SerializedName("Name")
    String Name;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EHSSubCategoryModel)) return false;

        EHSSubCategoryModel that = (EHSSubCategoryModel) obj;


        if ((ID != null && !ID.equalsIgnoreCase(that.ID)))
            return false;


        return true;     }
}
