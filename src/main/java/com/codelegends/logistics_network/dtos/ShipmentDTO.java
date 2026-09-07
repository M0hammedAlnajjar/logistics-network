package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.Entities.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    private Long id;
    private LocalDate shipmentDate;
    private String status;
    private BigDecimal totalWeight;
    private Long warehouseId;
    private Long customerId;
    private Long carrierId;

    public static ShipmentDTO convertToDTO(Shipment entity) {
        if (entity == null) {
            return null;
        }
        return ShipmentDTO.builder()
                .id(entity.getId())
                .shipmentDate(entity.getShipmentDate())
                .status(entity.getStatus())
                .totalWeight(entity.getTotalWeight())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .build();
    }

    public static List<ShipmentDTO> convertToDTO(List<Shipment> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(ShipmentDTO::convertToDTO).toList();
    }

    public Shipment toEntity() {
        Shipment entity = new Shipment();
        entity.setShipmentDate(shipmentDate);
        entity.setStatus(status);
        entity.setTotalWeight(totalWeight);
        if (warehouseId != null) {
            Warehouse warehouse = new Warehouse();
            warehouse.setId(warehouseId);
            entity.setWarehouse(warehouse);
        }
        if (customerId != null) {
            Customer customer = new Customer();
            customer.setId(customerId);
            entity.setCustomer(customer);
        }
        if (carrierId != null) {
            Carrier carrier = new Carrier();
            carrier.setId(carrierId);
            entity.setCarrier(carrier);
        }
        return entity;
    }
}
