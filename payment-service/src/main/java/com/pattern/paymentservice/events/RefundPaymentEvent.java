package com.pattern.paymentservice.events;

import com.pattern.paymentservice.models.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefundPaymentEvent {
    private Order order;
    private String reason;
}
