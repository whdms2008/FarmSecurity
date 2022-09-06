package com.example.farmsecurity.Request;

import com.google.gson.annotations.SerializedName;

public class LogRequest {
    @SerializedName("member_id")
    public String MemberId;

    public String getMemberId() { return MemberId; }

    public void setMemberId(String MemberId) { this.MemberId = MemberId; }
}
