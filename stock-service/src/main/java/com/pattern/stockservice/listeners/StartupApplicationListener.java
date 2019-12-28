package com.pattern.stockservice.listeners;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pattern.stockservice.model.Product;
import com.pattern.stockservice.repository.ProductRepository;

@Log4j2
@AllArgsConstructor
@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Creating default products");
        log.debug("Created product {}", productRepository.save(new Product(3L)));
        log.debug("Created product {}", productRepository.save(new Product(4L)));
        log.debug("Created product {}", productRepository.save(new Product(10L)));
    }
}
