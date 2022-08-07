package com.example.farmsecurity.Response;

import com.google.gson.annotations.SerializedName;

public class MainResponse {
    @SerializedName("id")
    public String getId;

    @SerializedName("pw")
    public String getPassword;

    @SerializedName("name")
    public String getName;

    @SerializedName("address")
    public String getAddress;

    @SerializedName("phone")
    public String getPhone;

    public String GetId() { return getId; }

    public String GetPassword() { return getPassword; }

    public String GetName() {
        return getName;
    }

    public String GetAddress() { return getAddress; }

    public String GetPhone() { return getPhone; }

    public void SetId(String getId) { this.getId = getId; }

    public void SetPassword(String getPassword) { this.getPassword = getPassword; }

    public void SetName(String getName) {
        this.getName = getName;
    }

    public void SetAddress(String getAddress) { this.getAddress = getAddress; }

    public void SetPhone(String getPhone) { this.getPhone = getPhone; }
}

