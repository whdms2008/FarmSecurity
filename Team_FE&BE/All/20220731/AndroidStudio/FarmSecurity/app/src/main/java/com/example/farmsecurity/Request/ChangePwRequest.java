package com.example.farmsecurity.Request;

import com.google.gson.annotations.SerializedName;

public class ChangePwRequest {
    @SerializedName("pw")
    public String getPw;

    public String GetPw() { return getPw; }

    public void SetId(String getPw) { this.getPw = getPw; }

    public ChangePwRequest(String getPw){
        this.getPw = getPw;
    }
}
