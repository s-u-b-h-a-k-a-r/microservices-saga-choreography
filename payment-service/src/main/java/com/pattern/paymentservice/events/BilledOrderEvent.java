package com.pattern.paymentservice.events;

import com.pattern.paymentservice.models.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BilledOrderEvent {
    private final Order order;
}
