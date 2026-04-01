package com.hackathon.hacksmdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackathon.hacksmdc.model.Otp;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmail(String email);
}