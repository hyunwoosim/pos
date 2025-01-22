package com.mulook.pos.dto;

import com.mulook.pos.entity.Enum.PayStatus;
import com.mulook.pos.entity.Enum.PayType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {


    private String paymentKey;

    private PayType payType;

    private String tossOrderId;

    private String tossOrderName;

    private PayStatus payStatus;

    private int totalAmount;

    private String successUrl;
    private String failUrl;

    // 결제 요청시간
    private LocalDateTime requestedAt;

    // 결제 승인 시간
    private LocalDateTime approvedAt;

}