package com.pattern.orderservice.events;

import com.pattern.orderservice.model.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderDoneEvent {
    private Order order;
}