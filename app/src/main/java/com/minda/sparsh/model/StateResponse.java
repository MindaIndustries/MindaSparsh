package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class StateResponse implements Serializable {
    @SerializedName("Id")
    String Id;
    @SerializedName("state")
    String state;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof StateResponse)) return false;

        StateResponse that = (StateResponse) o;

        if (!state.equals(that.state)) return false;

        return true;
    }
}
