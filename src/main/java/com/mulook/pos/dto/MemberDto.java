package com.mulook.pos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String loginId;

    @NotEmpty(message = "이름은 필수입니다.")
    private String username;

    @Valid
    private PasswordDto passwordDto;

    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;


    public MemberDto() {
    }
}