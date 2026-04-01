package com.hackathon.hacksmdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackathon.hacksmdc.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
