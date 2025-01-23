package com.mulook.pos.dto;

import com.mulook.pos.entity.Enum.PayStatus;
import com.mulook.pos.entity.Enum.PayType;
import com.mulook.pos.entity.Payment;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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
    private String orderId;
    private int amount;
    private String requestedAt;

    public Payment toEntity() {

        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        String formattedNow = now.format(formatter);

        return Payment.builder()
            .payStatus(PayStatus.READY)
            .tossOrderId(orderId)
            .totalAmount(amount)
            .requestedAt(formattedNow)
            .build();
    }
}