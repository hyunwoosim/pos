package com.mulook.pos.repository;

import com.mulook.pos.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTossOrderId(String OrderId);
}