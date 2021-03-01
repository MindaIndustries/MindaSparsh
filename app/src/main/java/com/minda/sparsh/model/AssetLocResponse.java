package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

public class AssetLocResponse {

    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("NextLevel")
    String NextLevel;
    @SerializedName("Next")
    String Next;
    @SerializedName("Name")
    String Namecat3;
    @SerializedName("nextlevel")
    String nextlevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextLevel() {
        return NextLevel;
    }

    public void setNextLevel(String nextLevel) {
        NextLevel = nextLevel;
    }

    public String getNext() {
        return Next;
    }

    public void setNext(String next) {
        Next = next;
    }

    public String getNamecat3() {
        return Namecat3;
    }

    public void setNamecat3(String namecat3) {
        Namecat3 = namecat3;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AssetLocResponse)) return false;

        AssetLocResponse that = (AssetLocResponse) obj;

        if ((id!="0" && !id.equals(that.id)))
            return false;


        return true;    }

    public String getNextlevel() {
        return nextlevel;
    }

    public void setNextlevel(String nextlevel) {
        this.nextlevel = nextlevel;
    }
}
