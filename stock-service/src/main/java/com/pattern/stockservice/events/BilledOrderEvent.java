package com.pattern.stockservice.events;

import com.pattern.stockservice.model.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BilledOrderEvent {
    private Order order;
}