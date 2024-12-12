package com.mulook.pos.repository;

import com.mulook.pos.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

     Optional<Member>findByLoginId(String loginId);
}