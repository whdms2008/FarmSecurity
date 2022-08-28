package com.example.farmsecurity.Response;

import com.google.gson.annotations.SerializedName;

public class LogResponse {
    @SerializedName("num")
    public String getNum;

    @SerializedName("cameraName")
    public String getCameraName;

    @SerializedName("cameraNum")
    public String getCameraNum;

    @SerializedName("level")
    public String getLevel;

    @SerializedName("link")
    public String getLink;

    @SerializedName("time")
    public String getTime;

    public String GetNum() { return getNum; }

    public String GetCameraName() { return getCameraName; }

    public String GetCameraNum() {
        return getCameraNum;
    }

    public String GetLevel() { return getLevel; }

    public String GetLink() { return getLink; }

    public String GetTime() { return getTime; }

    public void SetNum(String getNum) { this.getNum = getNum; }

    public void SetCameraName(String getCameraName) { this.getCameraName = getCameraName; }

    public void SetCameraNum(String getCameraNum) { this.getCameraNum = getCameraNum; }

    public void SetLevel(String getLevel) { this.getLevel = getLevel; }

    public void SetLink(String getLink) { this.getLink = getLink; }

    public void SetTime(String getTime) { this.getTime = getTime; }
}
