package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 6/8/2018.
 */

public class GetAbnormalityImage_Model {
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    @SerializedName("ImagePathAfter")
    @Expose
    private String imagePathAfter;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Action")
    @Expose
    private String Action;


    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePathAfter() {
        return imagePathAfter;
    }

    public void setImagePathAfter(String imagePathAfter) {
        this.imagePathAfter = imagePathAfter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
