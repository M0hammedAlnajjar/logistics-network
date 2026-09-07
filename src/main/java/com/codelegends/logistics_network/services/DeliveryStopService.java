package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.DeliveryStop;
import com.codelegends.logistics_network.Entities.Route;
import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.repositories.DeliveryStopRepository;
import com.codelegends.logistics_network.repositories.RouteRepository;
import com.codelegends.logistics_network.repositories.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryStopService {

    private final DeliveryStopRepository deliveryStopRepository;
    private final RouteRepository routeRepository;
    private final ShipmentRepository shipmentRepository;

    public DeliveryStopService(
            DeliveryStopRepository deliveryStopRepository,
            RouteRepository routeRepository,
            ShipmentRepository shipmentRepository) {
        this.deliveryStopRepository = deliveryStopRepository;
        this.routeRepository = routeRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public DeliveryStop createDeliveryStop(DeliveryStop stop) {
        validateStop(stop);
        stop.setId(null);
        stop.setActive(true);
        stop.setRoute(getRoute(stop.getRoute()));
        stop.setShipment(getShipment(stop.getShipment()));
        return deliveryStopRepository.save(stop);
    }

    public List<DeliveryStop> getAllDeliveryStops() {
        return deliveryStopRepository.findAllByIsActiveTrue();
    }

    public DeliveryStop getDeliveryStopById(Long id) {
        validateId(id);
        return deliveryStopRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active delivery stop not found with ID: " + id));
    }

    public DeliveryStop updateDeliveryStop(Long id, DeliveryStop updatedStop) {
        validateStop(updatedStop);
        DeliveryStop stop = getDeliveryStopById(id);
        stop.setSequence(updatedStop.getSequence());
        stop.setAddress(updatedStop.getAddress());
        stop.setStatus(updatedStop.getStatus());
        stop.setEta(updatedStop.getEta());
        stop.setRoute(getRoute(updatedStop.getRoute()));
        stop.setShipment(getShipment(updatedStop.getShipment()));
        return deliveryStopRepository.save(stop);
    }

    public void softDeleteDeliveryStop(Long id) {
        DeliveryStop stop = getDeliveryStopById(id);
        stop.setActive(false);
        deliveryStopRepository.save(stop);
    }

    private void validateStop(DeliveryStop stop) {
        if (stop == null) {
            throw new IllegalArgumentException("Delivery stop cannot be null");
        }
        if (stop.getSequence() == null || stop.getSequence() <= 0) {
            throw new IllegalArgumentException("Delivery stop sequence must be greater than zero");
        }
        if (stop.getAddress() == null || stop.getAddress().isBlank()) {
            throw new IllegalArgumentException("Delivery stop address is required");
        }
        if (stop.getStatus() == null || stop.getStatus().isBlank()) {
            throw new IllegalArgumentException("Delivery stop status is required");
        }
        if (stop.getEta() == null) {
            throw new IllegalArgumentException("Delivery stop ETA is required");
        }
    }

    private Route getRoute(Route route) {
        if (route == null || route.getId() == null) {
            throw new IllegalArgumentException("Route ID is required");
        }
        return routeRepository.findByIdAndIsActiveTrue(route.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active route not found"));
    }

    private Shipment getShipment(Shipment shipment) {
        if (shipment == null || shipment.getId() == null) {
            throw new IllegalArgumentException("Shipment ID is required");
        }
        return shipmentRepository.findByIdAndIsActiveTrue(shipment.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active shipment not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Delivery stop ID must be greater than zero");
        }
    }
}
