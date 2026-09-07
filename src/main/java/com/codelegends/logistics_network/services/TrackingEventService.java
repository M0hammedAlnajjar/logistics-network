package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.Entities.TrackingEvent;
import com.codelegends.logistics_network.repositories.ShipmentRepository;
import com.codelegends.logistics_network.repositories.TrackingEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingEventService {

    private final TrackingEventRepository trackingEventRepository;
    private final ShipmentRepository shipmentRepository;

    public TrackingEventService(
            TrackingEventRepository trackingEventRepository,
            ShipmentRepository shipmentRepository) {
        this.trackingEventRepository = trackingEventRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public TrackingEvent createTrackingEvent(TrackingEvent event) {
        validateEvent(event);
        event.setId(null);
        event.setActive(true);
        event.setShipment(getShipment(event.getShipment()));
        return trackingEventRepository.save(event);
    }

    public List<TrackingEvent> getAllTrackingEvents() {
        return trackingEventRepository.findAllByIsActiveTrue();
    }

    public TrackingEvent getTrackingEventById(Long id) {
        validateId(id);
        return trackingEventRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active tracking event not found with ID: " + id));
    }

    public TrackingEvent updateTrackingEvent(Long id, TrackingEvent updatedEvent) {
        validateEvent(updatedEvent);
        TrackingEvent event = getTrackingEventById(id);
        event.setEventTime(updatedEvent.getEventTime());
        event.setLocation(updatedEvent.getLocation());
        event.setStatus(updatedEvent.getStatus());
        event.setNote(updatedEvent.getNote());
        event.setShipment(getShipment(updatedEvent.getShipment()));
        return trackingEventRepository.save(event);
    }

    public void softDeleteTrackingEvent(Long id) {
        TrackingEvent event = getTrackingEventById(id);
        event.setActive(false);
        trackingEventRepository.save(event);
    }

    private void validateEvent(TrackingEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Tracking event cannot be null");
        }
        if (event.getEventTime() == null) {
            throw new IllegalArgumentException("Tracking event time is required");
        }
        if (event.getLocation() == null || event.getLocation().isBlank()) {
            throw new IllegalArgumentException("Tracking event location is required");
        }
        if (event.getStatus() == null) {
            throw new IllegalArgumentException("Tracking event status is required");
        }
    }

    private Shipment getShipment(Shipment shipment) {
        if (shipment == null || shipment.getId() == null) {
            throw new IllegalArgumentException("Shipment ID is required");
        }
        return shipmentRepository.findByIdAndIsActiveTrue(shipment.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active shipment not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Tracking event ID must be greater than zero");
        }
    }
}
