package com.minda.sparsh.model;

import com.google.gson.annotations.SerializedName;

public class VersionModel {
    @SerializedName("VersionId")
    Integer VersionId;
    @SerializedName("AndriodVersion")
    String AndriodVersion;
    @SerializedName("Status")
    String Status;
    @SerializedName("IOSVersion")
    String IOSVersion;

    public Integer getVersionId() {
        return VersionId;
    }

    public void setVersionId(Integer versionId) {
        VersionId = versionId;
    }

    public String getAndriodVersion() {
        return AndriodVersion;
    }

    public void setAndriodVersion(String andriodVersion) {
        AndriodVersion = andriodVersion;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getIOSVersion() {
        return IOSVersion;
    }

    public void setIOSVersion(String IOSVersion) {
        this.IOSVersion = IOSVersion;
    }
}
