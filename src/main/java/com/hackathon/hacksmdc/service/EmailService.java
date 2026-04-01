package com.hackathon.hacksmdc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp) {

        String subject = "🔐 Hackathon OTP Verification";

        String body = """
            Hello 👋,

            Your OTP for SMDC Hackathon Registration is:

            🔑 OTP: %s

            ⏳ Valid for 10 minutes.

            If you did not request this, please ignore this email.

            🚀 All the best for the hackathon!
            - HackSMDC Team
            """.formatted(otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}