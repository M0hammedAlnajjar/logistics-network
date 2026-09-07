package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.TrackingEventDTO;
import com.codelegends.logistics_network.services.TrackingEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracking-events")
public class TrackingEventController {

    private final TrackingEventService trackingEventService;

    public TrackingEventController(TrackingEventService trackingEventService) {
        this.trackingEventService = trackingEventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackingEventDTO create(@Valid @RequestBody TrackingEventDTO trackingEventDTO) {
        return TrackingEventDTO.convertToDTO(trackingEventService.createTrackingEvent(trackingEventDTO.toEntity()));
    }

    @GetMapping
    public List<TrackingEventDTO> getAll() {
        return TrackingEventDTO.convertToDTO(trackingEventService.getAllTrackingEvents());
    }

    @GetMapping("/{id}")
    public TrackingEventDTO getById(@PathVariable Long id) {
        return TrackingEventDTO.convertToDTO(trackingEventService.getTrackingEventById(id));
    }

    @PutMapping("/{id}")
    public TrackingEventDTO update(
            @PathVariable Long id,
            @Valid @RequestBody TrackingEventDTO trackingEventDTO) {
        return TrackingEventDTO.convertToDTO(
                trackingEventService.updateTrackingEvent(id, trackingEventDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        trackingEventService.softDeleteTrackingEvent(id);
    }
}
