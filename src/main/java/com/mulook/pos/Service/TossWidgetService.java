package com.mulook.pos.Service;

import com.mulook.pos.dto.PaymentRequestDto;
import com.mulook.pos.dto.PaymentSuccessDto;
import com.mulook.pos.entity.Enum.PayStatus;
import com.mulook.pos.entity.Enum.PayType;
import com.mulook.pos.entity.Payment;
import com.mulook.pos.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TossWidgetService {


    private final PaymentRepository paymentRepository;

    @Transactional
    public void successPayment(JSONObject jsonObject) {
        PaymentSuccessDto paymentSuccessDto = mapDto(jsonObject);

        Optional<Payment> paymentOptional = paymentRepository.findByTossOrderId(
            paymentSuccessDto.getTossOrderId());
        Payment payment = paymentOptional.orElseThrow(
            () -> new IllegalArgumentException("orderId로 찾을 수 없음"));


        payment.successPayment(paymentSuccessDto.getPaymentKey(),
                               paymentSuccessDto.getPayType(),
                               paymentSuccessDto.getTossOrderName(),
                               PayStatus.DONE,
                               paymentSuccessDto.getMethod(),
                               paymentSuccessDto.getProvider(),
                               paymentSuccessDto.getApprovedAt()
                               );

        paymentRepository.save(payment);
        System.out.println("########### 성공 ##############");
    }

    @Transactional
    public void savePayment(PaymentRequestDto paymentRequestDto) {
        System.out.println("############# svaePayment Service #############");
        System.out.println("paymentRequestDto = " + paymentRequestDto.getOrderId());
        System.out.println("paymentRequestDto.getRequestedAt() = " + paymentRequestDto.getRequestedAt());
        System.out.println("############# svaePayment Service #############");
        Payment payment = paymentRequestDto.toEntity();
        paymentRepository.save(payment);
    }

    @Transactional
    public void verify(String paymentKey ,String orderId, int amount) {

        Optional<Payment> paymentOptional = paymentRepository.findByTossOrderId(orderId);
        Payment payment = paymentOptional.orElseThrow(
            () -> new IllegalArgumentException("orderId로 찾을 수 없음"));

        // amount가 Int여서 equals를 사용 불가 int는 기본 타입, Integer는 객체 타입
        if(payment.getTotalAmount() != amount){
            System.out.println("##########verify Service ##########");
            System.out.println("저장된 결제 값과 최종 결제 값이 다르다");
            System.out.println("##########verify Service ##########");
        }
        System.out.println("##########verify Service ##########");
        System.out.println("저장된 결제 값과 최종 결제 값이 같다");
        System.out.println("##########verify Service ##########");

        // 결제 인증 완료로 변경
        payment.updatePayStatus(PayStatus.IN_PROGRESS);

        paymentRepository.save(payment);
    }

    private PaymentSuccessDto mapDto(JSONObject jsonObject) {

        JSONObject easyPay = (JSONObject) jsonObject.get("easyPay");
        String provider = (String) easyPay.get("provider");


        return PaymentSuccessDto.builder()
            .paymentKey((String) jsonObject.get("paymentKey"))
            .tossOrderId((String) jsonObject.get("orderId"))
            .tossOrderName((String) jsonObject.get("orderName"))
            .method((String) jsonObject.get("method"))
            .provider(provider)
            .payType(PayType.valueOf((String) jsonObject.get("type")))
            .payStatus(PayStatus.valueOf((String) jsonObject.get("status")))
            .approvedAt(OffsetDateTime.parse((String) jsonObject.get("approvedAt")).toString())
            .build();
    }

}