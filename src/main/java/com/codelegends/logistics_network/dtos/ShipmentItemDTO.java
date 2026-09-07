package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Product;
import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.Entities.ShipmentItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentItemDTO {

    private Long id;

    @NotNull(message = "Shipment item quantity is required")
    @Positive(message = "Shipment item quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Shipment ID is required")
    @Positive(message = "Shipment ID must be greater than zero")
    private Long shipmentId;

    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be greater than zero")
    private Long productId;

    public static ShipmentItemDTO convertToDTO(ShipmentItem entity) {
        if (entity == null) return null;
        return ShipmentItemDTO.builder().id(entity.getId())
                .quantity(entity.getQuantity())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .productId(entity.getProduct() == null ? null : entity.getProduct().getId())
                .build();
    }

    public static List<ShipmentItemDTO> convertToDTO(List<ShipmentItem> entities) {
        return entities == null ? List.of()
                : entities.stream().map(ShipmentItemDTO::convertToDTO).toList();
    }

    public ShipmentItem toEntity() {
        ShipmentItem entity = new ShipmentItem();
        entity.setQuantity(quantity);
        if (shipmentId != null) {
            Shipment shipment = new Shipment();
            shipment.setId(shipmentId);
            entity.setShipment(shipment);
        }
        if (productId != null) {
            Product product = new Product();
            product.setId(productId);
            entity.setProduct(product);
        }
        return entity;
    }
}
