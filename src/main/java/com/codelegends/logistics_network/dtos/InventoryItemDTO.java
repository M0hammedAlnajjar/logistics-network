package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.InventoryItem;
import com.codelegends.logistics_network.Entities.Product;
import com.codelegends.logistics_network.Entities.Warehouse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemDTO {

    private Long id;

    @NotNull(message = "Inventory quantity is required")
    @PositiveOrZero(message = "Inventory quantity cannot be negative")
    private Integer quantity;

    @NotBlank(message = "Shelf location is required")
    @Size(max = 50, message = "Shelf location must not exceed 50 characters")
    private String shelfLocation;

    @NotNull(message = "Warehouse ID is required")
    @Positive(message = "Warehouse ID must be greater than zero")
    private Long warehouseId;

    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be greater than zero")
    private Long productId;

    public static InventoryItemDTO convertToDTO(InventoryItem entity) {
        if (entity == null) return null;
        return InventoryItemDTO.builder().id(entity.getId())
                .quantity(entity.getQuantity()).shelfLocation(entity.getShelfLocation())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .productId(entity.getProduct() == null ? null : entity.getProduct().getId())
                .build();
    }

    public static List<InventoryItemDTO> convertToDTO(List<InventoryItem> entities) {
        return entities == null ? List.of()
                : entities.stream().map(InventoryItemDTO::convertToDTO).toList();
    }

    public InventoryItem toEntity() {
        InventoryItem entity = new InventoryItem();
        entity.setQuantity(quantity);
        entity.setShelfLocation(shelfLocation);
        if (warehouseId != null) {
            Warehouse warehouse = new Warehouse();
            warehouse.setId(warehouseId);
            entity.setWarehouse(warehouse);
        }
        if (productId != null) {
            Product product = new Product();
            product.setId(productId);
            entity.setProduct(product);
        }
        return entity;
    }
}
