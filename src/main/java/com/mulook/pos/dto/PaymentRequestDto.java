package com.mulook.pos.dto;

import com.mulook.pos.entity.Enum.PayStatus;
import com.mulook.pos.entity.Enum.PayType;
import com.mulook.pos.entity.Payment;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequestDto {
    // 결제 요청을 받을 Dto
    private PayStatus payStatus;
    private String tossOrderId;
    private int amount;
    private LocalDateTime requestedAt;

    public Payment toEntity() {
        return Payment.builder()
            .payStatus(PayStatus.READY)
            .tossOrderId(UUID.randomUUID().toString())
            .totalAmount(amount)
            .requestedAt(LocalDateTime.now())
            .build();
    }
}