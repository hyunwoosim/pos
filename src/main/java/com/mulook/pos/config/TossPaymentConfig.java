package com.mulook.pos.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPaymentConfig {

    @Value("${payment.toss.test_client_api_key}")
    private String clientApiKey;

    @Value("${payment.toss.test_secrete_api_key}")
    private String secretKey;

    @Value("${payment.toss.success_url}")
    private String successUrl;

    @Value("${payment.toss.fail_url}")
    private String failUrl;
}