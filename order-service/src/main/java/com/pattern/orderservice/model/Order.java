package com.pattern.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private Long productId;

    private Long quantity;

    private BigDecimal value;

    private OrderStatus status;

    /*
     * Should move it to apart table
     */
    private String canceledReason;

    public enum OrderStatus {
        NEW, DONE, CANCELED
    }
}