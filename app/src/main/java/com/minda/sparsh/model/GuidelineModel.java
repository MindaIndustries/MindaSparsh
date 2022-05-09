package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class GuidelineModel {
    @SerializedName("GuidelineID")
    int GuidelineID;
    @SerializedName("Description")
    String Description;
    @SerializedName("ApplicableID")
    int ApplicableID;
    @SerializedName("Title")
    String Title;

    public int getGuidelineID() {
        return GuidelineID;
    }

    public void setGuidelineID(int guidelineID) {
        GuidelineID = guidelineID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getApplicableID() {
        return ApplicableID;
    }

    public void setApplicableID(int applicableID) {
        ApplicableID = applicableID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
