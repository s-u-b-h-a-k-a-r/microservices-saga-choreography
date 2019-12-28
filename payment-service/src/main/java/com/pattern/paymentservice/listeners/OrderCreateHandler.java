package com.pattern.paymentservice.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.pattern.paymentservice.converters.Converter;
import com.pattern.paymentservice.events.OrderCreatedEvent;
import com.pattern.paymentservice.service.PaymentService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@AllArgsConstructor
public class OrderCreateHandler {

	private final Converter converter;
	private final PaymentService paymentService;

	@RabbitListener(queues = { "${queue.order-create}" })
	public void handle(@Payload String payload) {
		log.debug("Handling a created order event {}", payload);
		OrderCreatedEvent event = converter.toObject(payload, OrderCreatedEvent.class);
		paymentService.charge(event.getOrder());
	}
}
