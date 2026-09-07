package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.Entities.TrackingEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class TrackingEventDTO {

    private Long id;

    @NotNull(message = "Tracking event time is required")
    @PastOrPresent(message = "Tracking event time cannot be in the future")
    private LocalDateTime eventTime;

    @NotBlank(message = "Tracking event location is required")
    @Size(max = 150, message = "Tracking event location must not exceed 150 characters")
    private String location;

    @NotBlank(message = "Tracking event status is required")
    @Size(max = 50, message = "Tracking event status must not exceed 50 characters")
    private String status;

    @Size(max = 500, message = "Tracking event note must not exceed 500 characters")
    private String note;

    @NotNull(message = "Shipment ID is required")
    @Positive(message = "Shipment ID must be greater than zero")
    private Long shipmentId;

    public static TrackingEventDTO convertToDTO(TrackingEvent entity) {
        if (entity == null) return null;
        return TrackingEventDTO.builder().id(entity.getId())
                .eventTime(entity.getEventTime()).location(entity.getLocation())
                .status(entity.getStatus()).note(entity.getNote())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .build();
    }

    public static List<TrackingEventDTO> convertToDTO(List<TrackingEvent> entities) {
        return entities == null ? List.of()
                : entities.stream().map(TrackingEventDTO::convertToDTO).toList();
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
