package com.mulook.pos.controller;

import com.mulook.pos.Service.MemberService;
import com.mulook.pos.Service.redis.EmailSenderService;
import com.mulook.pos.Service.redis.EmailVerificationService;
import com.mulook.pos.dto.MemberDto;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailVerificationService emailVerificationService;
    private final EmailSenderService emailSenderService;


    @GetMapping("/member/new")
    public String createMemberForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());

        return "members/newMember.html";
    }

    // 회원 가입
    @PostMapping("/member/join")
    public String joinMembership(@Valid MemberDto memberDto, BindingResult result, Model model) {

        boolean isValid = memberService.validateDuplicateMemberID(memberDto.getLoginId());

        if (!isValid) {
            model.addAttribute("idError", "이미 존재하는 아이디입니다.");
            return "members/newMember.html"; // 아이디 중복 시 다시 회원가입 페이지로 리턴
        }

        // 에러에 맞게 문구 나가기
        if (result.hasErrors()) {
            Map<String, String> validatorResult = memberService.validateHandling(result);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "members/newMember.html";
        }

        // 비밀번호, 비밀번호 확인이 일치하는지 검사
        if (!memberDto.getPasswordDto().getPassword()
            .equals(memberDto.getPasswordDto().getConfirmPassword())) {

            return "members/newMember.html";
        }

        memberService.join(memberDto);

        return "redirect:/";
    }

    // Ajax를 사용해서 Id 중복 검사
    @GetMapping("/member/validateLoginId")
    @ResponseBody
    public ResponseEntity<Boolean> validateLoginId(@RequestParam("loginId") String loginId) {
        try {

            System.out.println("##########Controller1########");
            System.out.println("loginId = " + loginId);
            System.out.println("##########Controller1############");

            boolean isValid = memberService.validateDuplicateMemberID(loginId);

            System.out.println("##########Controller2@@@@@@@@@@@@@@@@");
            System.out.println("loginId = " + isValid);
            System.out.println("##########Controller2@@@@@@@@@@@@@@@");
            return ResponseEntity.ok(isValid);
        } catch (IllegalStateException e) {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/send-verify-email")
    public ResponseEntity<String> SendVerifyEmail(@RequestParam String email) {
        System.out.println("########## verifyEmail controller1 ########");
        System.out.println("########## verifyEmail controller 1########");


        System.out.println("########## verifyEmail controller 2########");
        System.out.println("email = " + email);
        System.out.println("########## verifyEmail controller2 ########");

        // 코드 생성
        String code = emailVerificationService.generateVerificationToken(email);

        System.out.println("########## verifyEmail controller3 ########");
        System.out.println("code = " + code);
        System.out.println("########## verifyEmail controller3 ########");

        // 토큰과 함께 이메일 발송
        emailSenderService.sendVerificationEmail(email, code);

        return ResponseEntity.ok("전송");
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String code) {
        if (emailVerificationService.verifyToken(code)) {
            return ResponseEntity.ok("검증 성공");
        }
        return ResponseEntity.status(401).body("실패");
    }
}