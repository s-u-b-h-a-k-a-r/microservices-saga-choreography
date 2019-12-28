package com.pattern.stockservice.events;

import com.pattern.stockservice.model.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCanceledEvent {
    private final Order order;
    private final String reason;
}
