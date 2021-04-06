package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

public class BindGroupResponse {
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
        if (!(obj instanceof BindGroupResponse)) return false;

        BindGroupResponse that = (BindGroupResponse) obj;

        if ((id != "0" && !id.equals(that.id)))
            return false;


        return true;
    }
}
