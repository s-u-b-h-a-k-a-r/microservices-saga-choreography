package com.pattern.paymentservice.service;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pattern.paymentservice.events.BilledOrderEvent;
import com.pattern.paymentservice.models.Order;
import com.pattern.paymentservice.models.Payment;
import com.pattern.paymentservice.repository.PaymentRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void charge(Order order) {
        log.debug("Charging order {}", order);

        /*
         * Any business logic to confirm charge ...
         */

        Payment payment = createOrder(order);

        log.debug("Saving payment {}", payment);
        paymentRepository.save(payment);

        publish(order);
    }

    private void publish(Order order) {
        BilledOrderEvent billedOrderEvent = new BilledOrderEvent(order);
        log.debug("Publishing a billed order event {}", billedOrderEvent);
        publisher.publishEvent(billedOrderEvent);
    }

    private Payment createOrder(Order order) {
        return Payment.builder().paymentStatus(Payment.PaymentStatus.BILLED).valueBilled(order.getValue())
                .transactionId(order.getTransactionId()).orderId(order.getId()).build();
    }

    @Transactional
    public void refund(String transactionId, String reason) {
        log.debug("Refund Payment by transactionId {}", transactionId);
        Optional<Payment> paymentOptional = paymentRepository.findByTransactionId(transactionId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setPaymentStatus(Payment.PaymentStatus.REFUND);
            payment.setRefundReason(reason);
            paymentRepository.save(payment);
            log.debug("Payment {} was refund by {}", payment.getId(), reason);
        } else {
            log.error("Cannot find the Payment by transaction {} to refund", transactionId);
        }
    }
}
