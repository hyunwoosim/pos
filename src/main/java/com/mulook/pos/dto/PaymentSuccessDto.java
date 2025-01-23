package com.mulook.pos.dto;

import com.mulook.pos.entity.Enum.PayStatus;
import com.mulook.pos.entity.Enum.PayType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentSuccessDto {


    private String paymentKey;

    private PayType payType;

    private String tossOrderId;

    private String tossOrderName;

    private PayStatus payStatus;

    private String method;

    private String provider;


    private int totalAmount;


    // 결제 요청시간
    private String requestedAt;

    // 결제 승인 시간
    private String approvedAt;


    @Override
    public String toString() {
        return "PaymentSuccessDto{" +
            "paymentKey='" + paymentKey + '\'' +
            ", payType=" + payType +
            ", tossOrderId='" + tossOrderId + '\'' +
            ", tossOrderName='" + tossOrderName + '\'' +
            ", payStatus=" + payStatus +
            ", method='" + method + '\'' +
            ", provider='" + provider + '\'' +
            ", totalAmount=" + totalAmount +
            ", requestedAt=" + requestedAt +
            ", approvedAt=" + approvedAt +
            '}';
    }
}