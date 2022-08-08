package com.example.farmsecurity;

import com.google.gson.annotations.SerializedName;

public class CameraResponse {
    @SerializedName("result")
    public String resultCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
