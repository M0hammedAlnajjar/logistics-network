package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Product;
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
    private String name;
    private String sku;
    private BigDecimal weightKg;
    private String category;

    public static ProductDTO convertToDTO(Product entity) {
        if (entity == null) {
            return null;
        }
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sku(entity.getSku())
                .weightKg(entity.getWeightKg())
                .category(entity.getCategory())
                .build();
    }

    public static List<ProductDTO> convertToDTO(List<Product> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(ProductDTO::convertToDTO).toList();
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
