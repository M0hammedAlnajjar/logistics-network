package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.DeliveryStop;
import com.codelegends.logistics_network.Entities.Route;
import com.codelegends.logistics_network.Entities.Shipment;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStopDTO {

    private Long id;

    @NotNull(message = "Delivery stop sequence is required")
    @Positive(message = "Delivery stop sequence must be greater than zero")
    private Integer sequence;

    @NotBlank(message = "Delivery stop address is required")
    @Size(max = 250, message = "Delivery stop address must not exceed 250 characters")
    private String address;

    @NotBlank(message = "Delivery stop status is required")
    @Size(max = 50, message = "Delivery stop status must not exceed 50 characters")
    private String status;

    @NotNull(message = "Delivery stop ETA is required")
    @FutureOrPresent(message = "Delivery stop ETA cannot be in the past")
    private LocalDateTime eta;

    @NotNull(message = "Route ID is required")
    @Positive(message = "Route ID must be greater than zero")
    private Long routeId;

    @NotNull(message = "Shipment ID is required")
    @Positive(message = "Shipment ID must be greater than zero")
    private Long shipmentId;

    public static DeliveryStopDTO convertToDTO(DeliveryStop entity) {
        if (entity == null) return null;
        return DeliveryStopDTO.builder().id(entity.getId())
                .sequence(entity.getSequence()).address(entity.getAddress())
                .status(entity.getStatus()).eta(entity.getEta())
                .routeId(entity.getRoute() == null ? null : entity.getRoute().getId())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .build();
    }

    public static List<DeliveryStopDTO> convertToDTO(List<DeliveryStop> entities) {
        return entities == null ? List.of()
                : entities.stream().map(DeliveryStopDTO::convertToDTO).toList();
    }

    public DeliveryStop toEntity() {
        DeliveryStop entity = new DeliveryStop();
        entity.setSequence(sequence);
        entity.setAddress(address);
        entity.setStatus(status);
        entity.setEta(eta);
        if (routeId != null) {
            Route route = new Route();
            route.setId(routeId);
            entity.setRoute(route);
        }
        if (shipmentId != null) {
            Shipment shipment = new Shipment();
            shipment.setId(shipmentId);
            entity.setShipment(shipment);
        }
        return entity;
    }
}
