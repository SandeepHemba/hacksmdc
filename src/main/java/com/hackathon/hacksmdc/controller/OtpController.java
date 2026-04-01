package com.hackathon.hacksmdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hackathon.hacksmdc.dto.*;
import com.hackathon.hacksmdc.service.OtpService;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin
public class OtpController {

    @Autowired
    private OtpService otpService;

    // 🔹 SEND OTP (WITH FULL DATA)
    @PostMapping("/send")
    public String sendOtp(@RequestBody OtpRequest request) {
        return otpService.generateOtpAndStoreData(
                request.getEmail(),
                request.getRegistrationData()
        );
    }

    // 🔹 VERIFY OTP + REGISTER
    @PostMapping("/verify")
    public String verifyOtp(@RequestBody VerifyOtpRequest request) {
        return otpService.verifyAndRegister(
                request.getEmail(),
                request.getOtp()
        );
    }
}