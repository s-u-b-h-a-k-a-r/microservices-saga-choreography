package com.pattern.orderservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pattern.orderservice.events.OrderCreatedEvent;
import com.pattern.orderservice.model.Order;
import com.pattern.orderservice.repository.OrderRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Order createOrder(Order order) {
        order.setTransactionId(UUID.randomUUID().toString());
        order.setStatus(Order.OrderStatus.NEW);

        publish(order);

        log.debug("Saving an order {}", order);
        return repository.save(order);
    }

    private void publish(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(order);
        log.debug("Publishing an order created event {}", event);
        publisher.publishEvent(event);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void updateOrderAsDone(String transactionId) {
        Order.OrderStatus status = Order.OrderStatus.DONE;
        log.debug("Updating Order {} to {}", transactionId, status);
        Optional<Order> orderOptional = repository.findByTransactionId(transactionId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            repository.save(order);
        } else {
            log.error("Cannot update Order to {}, Order {} not found", status, transactionId);
        }
    }

    @Transactional
    public void cancelOrder(String transactionId, String reason) {
        log.debug("Canceling Order by transaction {}", transactionId);
        Optional<Order> optionalOrder = repository.findByTransactionId(transactionId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(Order.OrderStatus.CANCELED);
            order.setCanceledReason(reason);
            repository.save(order);
            log.debug("Order {} was canceled by {}", order.getId(), reason);
        } else {
            log.error("Cannot find an Order by transaction {}", transactionId);
        }
    }
}
