package com.mulook.pos.Service;

import com.mulook.pos.dto.MemberDto;
import com.mulook.pos.entity.Member;
import com.mulook.pos.repository.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    // 회원 가입
    public void join(MemberDto memberDto) {

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(memberDto.getPasswordDto().getPassword());

        Member member = new Member();
        member.joinMembership(memberDto.getLoginId(), memberDto.getUsername(), memberDto.getEmail(),encodePassword);

        memberRepository.save(member);
    }

    // 로그인 ID 중복 확인
    // 중복 없으면 true 반환
    // 중복 있으면 false 반환
    @Transactional
    public boolean validateDuplicateMemberID(String loginId) {
        System.out.println("###########SERVICE111###########");
        System.out.println("loginId = " + loginId);
        System.out.println("###########SERVICE1111###########");

        Optional<Member> byLoginId = memberRepository.findByLoginId(loginId);

        System.out.println("###########SERVICE2222##########");
        System.out.println("byLoginId = " + byLoginId);
        System.out.println("###########SERVICE2222###########");

        return byLoginId.isEmpty(); // 중복된 아이디가 없으면 true 반환
    }


    // 회원 가입 시 빈칸으로 제출 시 유효성 검사
    public Map<String, String> validateHandling(BindingResult result) {
        Map<String, String> validateResult = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validateResult.put(validKeyName, error.getDefaultMessage());
        }
        return validateResult;
    }

}