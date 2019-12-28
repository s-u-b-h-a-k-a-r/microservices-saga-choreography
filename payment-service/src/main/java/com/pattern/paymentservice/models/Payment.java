package com.pattern.paymentservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private Long orderId;

    private BigDecimal valueBilled;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    /*
     * I know it's ugly
     */
    private String refundReason;

    public enum PaymentStatus {
        BILLED, REFUND
    }

}
