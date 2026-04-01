package com.hackathon.hacksmdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackathon.hacksmdc.model.Team;

public interface TeamRepository extends JpaRepository<Team, String> {
}
