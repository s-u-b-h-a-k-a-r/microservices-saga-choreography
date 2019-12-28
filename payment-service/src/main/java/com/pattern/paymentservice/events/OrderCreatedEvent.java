package com.pattern.paymentservice.events;

import com.pattern.paymentservice.models.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderCreatedEvent {
    private Order order;
}