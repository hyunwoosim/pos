package com.mulook.pos.Service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ValidateHandlingService {


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