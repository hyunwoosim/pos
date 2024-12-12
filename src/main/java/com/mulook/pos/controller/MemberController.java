package com.mulook.pos.controller;

import com.mulook.pos.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/member/new")
    public String createMember(Model model) {
        model.addAttribute("memberDto", new MemberDto());

        return "members/newMember.html";
    }

}