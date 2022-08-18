package com.example.farmsecurity.Response;

import com.google.gson.annotations.SerializedName;

public class IdResponse {
    @SerializedName("id")
    public String getId;

    public String GetId() { return getId; }

    public void SetId(String getId) { this.getId = getId; }
}

