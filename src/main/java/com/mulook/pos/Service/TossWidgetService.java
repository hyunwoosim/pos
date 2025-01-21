package com.mulook.pos.Service;

import com.mulook.pos.dto.PaymentRequestDto;
import com.mulook.pos.entity.Payment;
import com.mulook.pos.repository.PaymentRepository;
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
        System.out.println("paymentRequestDto = " + paymentRequestDto);
        System.out.println("############# svaePayment Service #############");
        Payment payment = paymentRequestDto.toEntity();
        paymentRepository.save(payment);
    }
}