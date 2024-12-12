package com.mulook.pos.repository;

import com.mulook.pos.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByLoginId(String loginId);
}