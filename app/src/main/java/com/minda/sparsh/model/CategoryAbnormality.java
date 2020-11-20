package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CategoryAbnormality {
    @SerializedName("id")
    public
    int id;
    @SerializedName("category")
    String category;

    public CategoryAbnormality(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}

