package com.mulook.pos.Service;

import com.mulook.pos.dto.PaymentRequestDto;
import com.mulook.pos.entity.Payment;
import com.mulook.pos.repository.PaymentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TossWidgetService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void savePayment(PaymentRequestDto paymentRequestDto) {
        System.out.println("############# svaePayment Service #############");
        System.out.println("paymentRequestDto = " + paymentRequestDto.getOrderId());
        System.out.println("############# svaePayment Service #############");
        Payment payment = paymentRequestDto.toEntity();
        paymentRepository.save(payment);
    }

    public void verify(String paymentKey ,String orderId, int amount) {

        Optional<Payment> TossOrderId = paymentRepository.findByTossOrderId(orderId);
        System.out.println("##########verify Service ##########");
        System.out.println("TossOrderId = " + TossOrderId);
        System.out.println("##########verify Service ##########");

    }

}