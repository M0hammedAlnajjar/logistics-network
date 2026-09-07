package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.Entities.TrackingEvent;
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
public class TrackingEventDTO {

    private Long id;
    private LocalDateTime eventTime;
    private String location;
    private String status;
    private String note;
    private Long shipmentId;

    public static TrackingEventDTO convertToDTO(TrackingEvent entity) {
        if (entity == null) {
            return null;
        }
        return TrackingEventDTO.builder()
                .id(entity.getId())
                .eventTime(entity.getEventTime())
                .location(entity.getLocation())
                .status(entity.getStatus())
                .note(entity.getNote())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .build();
    }

    public static List<TrackingEventDTO> convertToDTO(List<TrackingEvent> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(TrackingEventDTO::convertToDTO).toList();
    }

    public TrackingEvent toEntity() {
        TrackingEvent entity = new TrackingEvent();
        entity.setEventTime(eventTime);
        entity.setLocation(location);
        entity.setStatus(status);
        entity.setNote(note);
        if (shipmentId != null) {
            Shipment shipment = new Shipment();
            shipment.setId(shipmentId);
            entity.setShipment(shipment);
        }
        return entity;
    }
}
