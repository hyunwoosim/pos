package com.mulook.pos.Service.redis;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    /**
     * 인증 토큰 생성 서비스
     */

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${email.verification.expiry:600}") // 기본값 600초
    private Long expiryTime;

    // 인증 토큰 생성
    public String generateVerificationToken(String email) {

        // 6자리 랜덤 숫자 인증코드 생성
        Random random = new Random();
        int code = 10000 + random.nextInt(9000000);
        String verificationCode = String.valueOf(code);

        // redis에 저장
        redisTemplate.opsForValue().set(verificationCode, email, expiryTime, TimeUnit.SECONDS);

        return verificationCode;
    }

    // 토큰 확인 로직
    public boolean verifyToken(String code) {
        String email = redisTemplate.opsForValue().get(code);

        if (email != null) {
            // 검증 성공 후 토큰 삭제
            redisTemplate.delete(code);
            return true;
        }
        return false;

    }



}