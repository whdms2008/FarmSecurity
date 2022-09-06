package com.example.farmsecurity.Request;

import com.google.gson.annotations.SerializedName;

public class CameraRequest {
    @SerializedName("cameraNum")
    public String inputNum;

    @SerializedName("cameraName")
    public String inputName;

    @SerializedName("id")
    public String inputId;

    public String getInputNum() { return inputNum; }

    public String getInputName() { return inputName; }

    public String getInputId() { return inputId; }

    public void setInputNum(String inputNum) { this.inputNum = inputNum; }

    public void setInputName(String inputName) { this.inputName = inputName; }

    public void setInputId(String inputId) { this.inputId = inputId; }

    public CameraRequest(String inputNum, String inputName, String inputId) {
        this.inputNum = inputNum;
        this.inputName = inputName;
        this.inputId = inputId;
    }
}
