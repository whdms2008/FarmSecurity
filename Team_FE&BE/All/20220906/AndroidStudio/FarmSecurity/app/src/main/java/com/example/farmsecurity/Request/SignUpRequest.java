package com.example.farmsecurity.Request;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("id")
    public String inputId;

    @SerializedName("pw")
    public String inputPassword;

    @SerializedName("name")
    public String inputName;

    @SerializedName("address")
    public String inputAddress;

    @SerializedName("phone")
    public String inputPhone;

    @SerializedName("token")
    public String inputToken;

    public String getInputId() { return inputId; }

    public String getInputPassword() { return inputPassword; }

    public String getInputName() {
        return inputName;
    }

    public String getInputAddress() { return inputAddress; }

    public String getInputPhone() { return inputPhone; }

    public String getInputToken() { return inputToken; }

    public void setInputId(String inputId) { this.inputId = inputId; }

    public void setInputPassword(String inputPassword) { this.inputPassword = inputPassword;}

    public void setInputName(String inputName) { this.inputName = inputName; }

    public void setInputAddress(String inputAddress) { this.inputAddress = inputAddress; }

    public void setInputPhone(String inputPhone) { this.inputPhone = inputPhone; }

    public void setInputToken(String inputToken) { this.inputToken = inputToken; }

    public SignUpRequest(String inputId, String inputPassword, String inputName, String inputAddress, String inputPhone, String inputToken) {
        this.inputId = inputId;
        this.inputPassword = inputPassword;
        this.inputName = inputName;
        this.inputAddress = inputAddress;
        this.inputPhone = inputPhone;
        this.inputToken = inputToken;
    }
}
