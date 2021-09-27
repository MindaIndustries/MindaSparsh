package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class DashboardImagesModel {
    @SerializedName("imgsrc")
    String imgsrc;
    @SerializedName("url")
    String url;

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
