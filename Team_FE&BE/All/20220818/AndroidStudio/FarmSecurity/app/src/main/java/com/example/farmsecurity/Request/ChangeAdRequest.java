package com.example.farmsecurity.Request;

import com.google.gson.annotations.SerializedName;

public class ChangeAdRequest {
    @SerializedName("address")
    public String getAddress;

    public String GetAddress() { return getAddress; }

    public void SetId(String getAddress) { this.getAddress = getAddress; }

    public ChangeAdRequest(String getAddress){
        this.getAddress = getAddress;
    }
}

