package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Product SKU is required")
    @Size(max = 50, message = "Product SKU must not exceed 50 characters")
    private String sku;

    @NotNull(message = "Product weight is required")
    @Positive(message = "Product weight must be greater than zero")
    private BigDecimal weightKg;

    @NotBlank(message = "Product category is required")
    @Size(max = 100, message = "Product category must not exceed 100 characters")
    private String category;

    public static ProductDTO convertToDTO(Product entity) {
        if (entity == null) return null;
        return ProductDTO.builder().id(entity.getId()).name(entity.getName())
                .sku(entity.getSku()).weightKg(entity.getWeightKg())
                .category(entity.getCategory()).build();
    }

    public static List<ProductDTO> convertToDTO(List<Product> entities) {
        return entities == null ? List.of()
                : entities.stream().map(ProductDTO::convertToDTO).toList();
    }

    public Product toEntity() {
        Product entity = new Product();
        entity.setName(name);
        entity.setSku(sku);
        entity.setWeightKg(weightKg);
        entity.setCategory(category);
        return entity;
    }
}
