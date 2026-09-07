package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.DeliveryStop;
import com.codelegends.logistics_network.Entities.Route;
import com.codelegends.logistics_network.Entities.Shipment;
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
    private Integer sequence;
    private String address;
    private String status;
    private LocalDateTime eta;
    private Long routeId;
    private Long shipmentId;

    public static DeliveryStopDTO convertToDTO(DeliveryStop entity) {
        if (entity == null) {
            return null;
        }
        return DeliveryStopDTO.builder()
                .id(entity.getId())
                .sequence(entity.getSequence())
                .address(entity.getAddress())
                .status(entity.getStatus())
                .eta(entity.getEta())
                .routeId(entity.getRoute() == null ? null : entity.getRoute().getId())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .build();
    }

    public static List<DeliveryStopDTO> convertToDTO(List<DeliveryStop> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(DeliveryStopDTO::convertToDTO).toList();
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
