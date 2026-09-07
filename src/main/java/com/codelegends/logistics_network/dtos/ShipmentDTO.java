package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.enums.ShipmentStatus;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.Shipment;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    private Long id;

    @NotNull(message = "Shipment date is required")
    private LocalDate shipmentDate;

    @NotNull(message = "Shipment status is required")
    private ShipmentStatus status;

    @NotNull(message = "Total weight is required")
    @PositiveOrZero(message = "Total weight cannot be negative")
    private BigDecimal totalWeight;

    @NotNull(message = "Warehouse ID is required")
    @Positive(message = "Warehouse ID must be greater than zero")
    private Long warehouseId;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be greater than zero")
    private Long customerId;

    @Positive(message = "Carrier ID must be greater than zero")
    private Long carrierId;

    public static ShipmentDTO convertToDTO(Shipment entity) {
        if (entity == null) return null;
        return ShipmentDTO.builder().id(entity.getId())
                .shipmentDate(entity.getShipmentDate()).status(entity.getStatus())
                .totalWeight(entity.getTotalWeight())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .build();
    }

    public static List<ShipmentDTO> convertToDTO(List<Shipment> entities) {
        return entities == null ? List.of()
                : entities.stream().map(ShipmentDTO::convertToDTO).toList();
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
