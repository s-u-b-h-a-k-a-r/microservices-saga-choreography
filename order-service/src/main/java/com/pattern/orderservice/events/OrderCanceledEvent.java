package com.pattern.orderservice.events;

import com.pattern.orderservice.model.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCanceledEvent {
    private Order order;
    private String reason;
}
