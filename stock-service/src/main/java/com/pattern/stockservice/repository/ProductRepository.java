package com.pattern.stockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pattern.stockservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
