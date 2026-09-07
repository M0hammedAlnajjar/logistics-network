package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.ProductDTO;
import com.codelegends.logistics_network.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductDTO productDTO) {
        return ProductDTO.convertToDTO(productService.createProduct(productDTO.toEntity()));
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return ProductDTO.convertToDTO(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return ProductDTO.convertToDTO(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ProductDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        return ProductDTO.convertToDTO(
                productService.updateProduct(id, productDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.softDeleteProduct(id);
    }
}
