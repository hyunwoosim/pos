package com.mulook.pos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String username;
    private String email;
    private String password;


    // === 비즈니스 로직 === //

    // 회원 가입


    public void joinMembership(String loginId, String username, String email, String encoderPassword) {
        this.loginId = loginId;
        this.username = username;
        this.email = email;
        this.password = encoderPassword;
    }
}