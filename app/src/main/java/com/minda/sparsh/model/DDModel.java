package com.minda.sparsh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmin on 7/31/2018.
 */

public class DDModel {
    @SerializedName("CountAbnormality")
    @Expose
    private Integer countAbnormality;
    @SerializedName("CountAbnormalityClosed")
    @Expose
    private Integer countAbnormalityClosed;
    @SerializedName("CountAbnormalityPending")
    @Expose
    private Integer countAbnormalityPending;

    public Integer getCountAbnormality() {
        return countAbnormality;
    }

    public void setCountAbnormality(Integer countAbnormality) {
        this.countAbnormality = countAbnormality;
    }

    public Integer getCountAbnormalityClosed() {
        return countAbnormalityClosed;
    }

    public void setCountAbnormalityClosed(Integer countAbnormalityClosed) {
        this.countAbnormalityClosed = countAbnormalityClosed;
    }

    public Integer getCountAbnormalityPending() {
        return countAbnormalityPending;
    }

    public void setCountAbnormalityPending(Integer countAbnormalityPending) {
        this.countAbnormalityPending = countAbnormalityPending;
    }
}
