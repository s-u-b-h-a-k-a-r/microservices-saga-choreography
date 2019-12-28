package com.pattern.orderservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pattern.orderservice.model.Order;
import com.pattern.orderservice.service.OrderService;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        log.debug("Post an order {}", order);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
