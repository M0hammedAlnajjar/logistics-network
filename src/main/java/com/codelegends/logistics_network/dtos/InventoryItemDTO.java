package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.InventoryItem;
import com.codelegends.logistics_network.Entities.Product;
import com.codelegends.logistics_network.Entities.Warehouse;
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
    private Integer quantity;
    private String shelfLocation;
    private Long warehouseId;
    private Long productId;

    public static InventoryItemDTO convertToDTO(InventoryItem entity) {
        if (entity == null) {
            return null;
        }
        return InventoryItemDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .shelfLocation(entity.getShelfLocation())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .productId(entity.getProduct() == null ? null : entity.getProduct().getId())
                .build();
    }

    public static List<InventoryItemDTO> convertToDTO(List<InventoryItem> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(InventoryItemDTO::convertToDTO).toList();
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
