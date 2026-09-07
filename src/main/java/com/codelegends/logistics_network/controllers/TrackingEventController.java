package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.TrackingEvent;
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
    public TrackingEvent create(@Valid @RequestBody TrackingEvent trackingEvent) {
        return trackingEventService.createTrackingEvent(trackingEvent);
    }

    @GetMapping
    public List<TrackingEvent> getAll() {
        return trackingEventService.getAllTrackingEvents();
    }

    @GetMapping("/{id}")
    public TrackingEvent getById(@PathVariable Long id) {
        return trackingEventService.getTrackingEventById(id);
    }

    @PutMapping("/{id}")
    public TrackingEvent update(
            @PathVariable Long id,
            @Valid @RequestBody TrackingEvent trackingEvent) {
        return trackingEventService.updateTrackingEvent(id, trackingEvent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        trackingEventService.softDeleteTrackingEvent(id);
    }
}
