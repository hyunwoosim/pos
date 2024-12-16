package com.mulook.pos.Service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    /**
     * 이메일 전송 서비스
     */

    private final JavaMailSender mailSender;



    public void sendVerificationEmail(String toEmail, String code) {
        System.out.println("#########EmailSenderService########");
        System.out.println("code =" + code);
        System.out.println("#########EmailSenderService########");

        String subject = "이메일 인증 코드";
        String text = "인증 코드는" + code + "입니다 5분 이내에 입력해주세요";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

}