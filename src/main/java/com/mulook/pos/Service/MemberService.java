package com.mulook.pos.Service;

import com.mulook.pos.dto.MemberDto;
import com.mulook.pos.entity.Member;
import com.mulook.pos.repository.MemberRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;
    private BCryptPasswordEncoder passwordEncoder;


    // 회원 가입
    public void join(MemberDto memberDto) {

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(memberDto.getPasswordDto().getPassword());

        Member member = new Member();
        member.joinMembership(memberDto.getLoginId(), memberDto.getUsername(), memberDto.getEmail(),encodePassword);

        memberRepository.save(member);
    }

    // 로그인 ID 중복 확인
    public void validateDuplicateMemberID(MemberDto memberDto) {
        List<Member> byLoginId = memberRepository.findByLoginId(memberDto.getLoginId());
        if (!byLoginId.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
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