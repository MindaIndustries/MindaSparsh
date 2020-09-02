package com.minda.sparsh.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EHSCategoryModel {
    @SerializedName("id")
    String id;
    @SerializedName("Name")
    String Name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(obj instanceof EHSCategoryModel)) return false;

        EHSCategoryModel that = (EHSCategoryModel) obj;


        if ((id != null && !id.equalsIgnoreCase(that.id)))
            return false;


        return true;    }
}
