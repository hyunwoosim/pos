package com.mulook.pos.entity;

import com.mulook.pos.entity.Enum.PayStatus;
import com.mulook.pos.entity.Enum.PayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long paymentId;

    private String paymentKey;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    private String tossOrderId;

   private String tossOrderName;

    @Enumerated(EnumType.STRING)
   private PayStatus payStatus;

   private int totalAmount;

   // 결제 요청시간
   private LocalDateTime requestedAt;

   // 결제 승인 시간
   private LocalDateTime approvedAt;


}