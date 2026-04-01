package com.hackathon.hacksmdc.service;

import com.hackathon.hacksmdc.dto.RegistrationRequest;
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

    public void sendRegistrationSuccessEmail(String toEmail, String teamId, RegistrationRequest data) {

        String subject = "🎉 Hackathon Registration Successful";

        String body = """
            Hello %s 👋,

            🎉 Your team has been successfully registered!

            🔖 Team ID: %s
            👥 Team Name: %s
            🏫 College: %s
            💻 Domain: %s

            📌 Team Members:
            - %s
            - %s
            - %s

            🚀 Keep this Team ID safe for future reference.

            All the best for the hackathon! 💪
            - HacksMDC Team
            """.formatted(
                data.getLeadName(),
                teamId,
                data.getTeamName(),
                data.getCollege(),
                data.getDomain(),
                getMember(data, 0),
                getMember(data, 1),
                getMember(data, 2)
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    private String getMember(RegistrationRequest data, int index) {
        if (data.getMembers() != null && data.getMembers().size() > index) {
            return data.getMembers().get(index);
        }
        return "-";
    }
}
