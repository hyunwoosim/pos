package com.mulook.pos.controller;

import com.mulook.pos.Service.MemberService;
import com.mulook.pos.dto.MemberDto;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private MemberService memberService;


    @GetMapping("/member/new")
    public String createMemberForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());

        return "members/newMember.html";
    }

    @PostMapping("/member/join")
    public String joinMembership(@Valid MemberDto memberDto, BindingResult result, Model model) {

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
}