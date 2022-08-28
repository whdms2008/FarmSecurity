package com.example.farmsecurity.Response;

import com.google.gson.annotations.SerializedName;

public class CameraLogResponse {
    @SerializedName("cameraNum")
    public String cameraNum;

    @SerializedName("cameraName")
    public String cameraName;

    @SerializedName("memId")
    public String memId;

    public String getCameraNum() {
        return cameraNum;
    }

    public String getCameraName() { return cameraName; }

    public String getMemId() { return memId; }

    public void setCameraNum(String cameraNum) {
        this.cameraNum = cameraNum;
    }

    public void setCameraName(String cameraName) { this.cameraName = cameraName; }

    public void setMemId(String memId) { this.memId = memId; }
}
