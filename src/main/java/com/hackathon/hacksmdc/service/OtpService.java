package com.hackathon.hacksmdc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.hacksmdc.dto.RegistrationRequest;
import com.hackathon.hacksmdc.model.Otp;
import com.hackathon.hacksmdc.repository.OtpRepository;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RegistrationService registrationService;

    // 🔥 TEMP STORAGE
    private Map<String, RegistrationRequest> tempStorage = new ConcurrentHashMap<>();

    // 🔹 SEND OTP + STORE DATA
    public String generateOtpAndStoreData(String email, RegistrationRequest data) {

        // 🔴 Check if OTP already exists
        Otp existingOtp = otpRepo.findByEmail(email).orElse(null);

        if (existingOtp != null) {
            // 🧹 delete old OTP (resend case)
            otpRepo.delete(existingOtp);
        }

        // 🔢 Generate new OTP
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(10)); // ⏱️

        otpRepo.save(otpEntity);

        // 🔥 Store latest form data (overwrite)
        tempStorage.put(email, data);

        // 📧 Send email
        emailService.sendOtp(email, otp);

        return existingOtp == null
                ? "OTP sent successfully!"
                : "OTP resent successfully!";
    }

    // 🔹 VERIFY OTP + COMPLETE REGISTRATION
    public String verifyAndRegister(String email, String enteredOtp) {

        Otp otpEntity = otpRepo.findByEmail(email).orElse(null);

        if (otpEntity == null) return "❌ Invalid OTP";

        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "❌ OTP expired";
        }

        if (!otpEntity.getOtp().equals(enteredOtp)) {
            return "❌ Incorrect OTP";
        }

        // 🔥 GET STORED DATA
        RegistrationRequest data = tempStorage.get(email);

        if (data == null) {
            return "❌ No registration data found!";
        }

        // 🔥 REGISTER TEAM
        String response = registrationService.registerTeam(data);

        // 🧹 CLEANUP
        tempStorage.remove(email);
        otpRepo.delete(otpEntity);

        return response;
    }
}