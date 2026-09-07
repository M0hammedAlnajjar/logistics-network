package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.Product;
import com.codelegends.logistics_network.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (product.getSku() == null || product.getSku().isBlank()) {
            throw new IllegalArgumentException("Product SKU is required");
        }

        if (productRepository.existsBySku(product.getSku())) {
            throw new IllegalArgumentException("Product SKU already exists");
        }

        if (product.getWeightKg() == null
                || product.getWeightKg().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product weight must be greater than zero");
        }

        if (product.getCategory() == null || product.getCategory().isBlank()) {
            throw new IllegalArgumentException("Product category is required");
        }

        product.setId(null);
        product.setActive(true);

        return productRepository.save(product);
    }
}
