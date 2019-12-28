package com.pattern.stockservice.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.pattern.stockservice.converters.Converter;
import com.pattern.stockservice.events.BilledOrderEvent;
import com.pattern.stockservice.exception.StockException;
import com.pattern.stockservice.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
@Component
public class BilledOrderHandler {

    private final Converter converter;
    private final ProductService productService;

    @RabbitListener(queues = { "${queue.billed-order}" })
    public void handle(@Payload String payload) {
        log.debug("Handling a billed order event {}", payload);
        BilledOrderEvent event = converter.toObject(payload, BilledOrderEvent.class);
        try {
            productService.updateQuantity(event.getOrder());
        } catch (StockException e) {
            log.error("Cannot update stock, reason: {}", e.getMessage());
        }
    }
}
