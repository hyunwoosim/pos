package com.mulook.pos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {

    @NotBlank(message = "비밀번호는 필수입니다.")
//    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자리에서 20자리 사이입니다.")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$",
//        message = "최소 하나의 숫자,소문자,대문자,특수문자를 포함해야하며 공백이 없어야합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword; // 비밀번호 일치 확인
}