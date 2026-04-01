package com.hackathon.hacksmdc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.hackathon.hacksmdc.model.TeamLead;

public interface TeamLeadRepository extends JpaRepository<TeamLead, Long> {
    boolean existsByEmail(String email);
}