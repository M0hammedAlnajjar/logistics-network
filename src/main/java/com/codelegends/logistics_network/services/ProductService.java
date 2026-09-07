package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.Product;
import com.codelegends.logistics_network.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        validateProduct(product);
        if (productRepository.existsBySku(product.getSku())) {
            throw new IllegalArgumentException("Product SKU already exists");
        }
        product.setId(null);
        product.setActive(true);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllByIsActiveTrue();
    }

    public Product getProductById(Long id) {
        validateId(id);
        return productRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active product not found with ID: " + id));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        validateProduct(updatedProduct);
        Product product = getProductById(id);
        if (productRepository.existsBySkuAndIdNot(updatedProduct.getSku(), id)) {
            throw new IllegalArgumentException("Product SKU already exists");
        }
        product.setName(updatedProduct.getName());
        product.setSku(updatedProduct.getSku());
        product.setWeightKg(updatedProduct.getWeightKg());
        product.setCategory(updatedProduct.getCategory());
        return productRepository.save(product);
    }

    public void softDeleteProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getSku() == null || product.getSku().isBlank()) {
            throw new IllegalArgumentException("Product SKU is required");
        }
        if (product.getWeightKg() == null
                || product.getWeightKg().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product weight must be greater than zero");
        }
        if (product.getCategory() == null || product.getCategory().isBlank()) {
            throw new IllegalArgumentException("Product category is required");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be greater than zero");
        }
    }
}
