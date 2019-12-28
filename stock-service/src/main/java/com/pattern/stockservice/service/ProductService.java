package com.pattern.stockservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pattern.stockservice.events.OrderCanceledEvent;
import com.pattern.stockservice.events.OrderDoneEvent;
import com.pattern.stockservice.exception.StockException;
import com.pattern.stockservice.model.Order;
import com.pattern.stockservice.model.Product;
import com.pattern.stockservice.repository.ProductRepository;

import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void updateQuantity(Order order) {
        log.debug("Start updating product {}", order.getProductId());

        Product product = getProduct(order);
        checkStock(order, product);
        updateStock(order, product);

        publishOrderDone(order);
    }

    private void updateStock(Order order, Product product) {
        product.setQuantity(product.getQuantity() - order.getQuantity());
        log.debug("Updating product {} with quantity {}", product.getId(), product.getQuantity());
        productRepository.save(product);
    }

    private void publishOrderDone(Order order) {
        OrderDoneEvent event = new OrderDoneEvent(order);
        log.debug("Publishing order done event {}", event);
        publisher.publishEvent(event);
    }

    private void checkStock(Order order, Product product) {
        log.debug("Checking, products available {}, products ordered {}", product.getQuantity(), order.getQuantity());
        if (product.getQuantity() < order.getQuantity()) {
            String message = "Product " + product.getId() + " is out of stock";
            publishCanceledOrder(order, message);
            throw new StockException(message);
        }
    }

    private void publishCanceledOrder(Order order, String reason) {
        OrderCanceledEvent event = new OrderCanceledEvent(order, reason);
        log.debug("Publishing canceled order event {}", event);
        publisher.publishEvent(event);
    }

    private Product getProduct(Order order) {
        Optional<Product> optionalProduct = productRepository.findById(order.getProductId());
        return optionalProduct.orElseThrow(() -> {
            String message = "Cannot find a product " + order.getProductId();
            publishCanceledOrder(order, message);
            return new StockException(message);
        });
    }
}
