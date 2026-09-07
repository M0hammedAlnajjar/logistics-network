package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Product;
import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.Entities.ShipmentItem;
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
    private Integer quantity;
    private Long shipmentId;
    private Long productId;

    public static ShipmentItemDTO convertToDTO(ShipmentItem entity) {
        if (entity == null) {
            return null;
        }
        return ShipmentItemDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .productId(entity.getProduct() == null ? null : entity.getProduct().getId())
                .build();
    }

    public static List<ShipmentItemDTO> convertToDTO(List<ShipmentItem> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(ShipmentItemDTO::convertToDTO).toList();
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
