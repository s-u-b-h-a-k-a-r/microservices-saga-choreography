package com.pattern.orderservice.listeners;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.pattern.orderservice.converters.Converter;
import com.pattern.orderservice.events.OrderDoneEvent;
import com.pattern.orderservice.service.OrderService;

@Log4j2
@Component
@AllArgsConstructor
public class OrderDoneEventHandler {

    private final Converter converter;
    private final OrderService orderService;

    @RabbitListener(queues = { "${queue.order-done}" })
    public void handleOrderDoneEvent(@Payload String payload) {
        log.debug("Handling a order done event {}", payload);
        OrderDoneEvent event = converter.toObject(payload, OrderDoneEvent.class);
        orderService.updateOrderAsDone(event.getOrder().getTransactionId());
    }
}
