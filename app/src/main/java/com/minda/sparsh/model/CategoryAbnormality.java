package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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
    public CategoryAbnormality(){

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryAbnormality)) return false;
        CategoryAbnormality that = (CategoryAbnormality) o;
        return Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory());
    }
}

