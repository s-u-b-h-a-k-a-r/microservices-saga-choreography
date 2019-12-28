package com.pattern.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pattern.paymentservice.models.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionId(String transactionId);
}
