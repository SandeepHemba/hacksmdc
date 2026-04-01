package com.hackathon.hacksmdc.dto;

import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private RegistrationRequest registrationData; // 🔥 NEW

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RegistrationRequest getRegistrationData() {
        return registrationData;
    }

    public void setRegistrationData(RegistrationRequest registrationData) {
        this.registrationData = registrationData;
    }
}