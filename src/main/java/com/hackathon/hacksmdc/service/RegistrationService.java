package com.hackathon.hacksmdc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.hacksmdc.dto.RegistrationRequest;
import com.hackathon.hacksmdc.model.*;
import com.hackathon.hacksmdc.repository.*;

@Service
public class RegistrationService {

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private TeamLeadRepository leadRepo;

    @Autowired
    private MemberRepository memberRepo;

    public String registerTeam(RegistrationRequest request) {

        // 🔴 Check duplicate email
        if (leadRepo.existsByEmail(request.getEmail())) {
            return "Email already registered!";
        }

        // 🟢 1. Save Team
        Team team = new Team();
        team.setTeamName(request.getTeamName());
        team.setCollege(request.getCollege());
        team.setDomain(request.getDomain());

        team = teamRepo.save(team);

        // 🔥 Generate custom ID
        String hackId = generateHackId(team.getId()); // using DB ID

        team.setTeamId(hackId);

        // Save again
        team = teamRepo.save(team);

        // 🟢 2. Save Team Lead
        TeamLead lead = new TeamLead();
        lead.setName(request.getLeadName());
        lead.setEmail(request.getEmail());
        lead.setPhone(request.getPhone());
        lead.setTeam(team);

        leadRepo.save(lead);

        // 🟢 3. Save Members (max 3)
        if (request.getMembers() != null) {
            for (String name : request.getMembers()) {
                if (name != null && !name.isBlank()) {
                    Member m = new Member();
                    m.setName(name);
                    m.setTeam(team);
                    memberRepo.save(m);
                }
            }
        }

        return "✅ Team Registered Successfully! Team ID: " + team.getTeamId();
    }

    private String generateHackId(long number) {
        return String.format("HACK-%03d", number);
    }
}