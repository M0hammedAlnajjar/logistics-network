package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shipment create(@Valid @RequestBody Shipment shipment) {
        return shipmentService.createShipment(shipment);
    }

    @GetMapping
    public List<Shipment> getAll() {
        return shipmentService.getAllShipments();
    }

    @GetMapping("/{id}")
    public Shipment getById(@PathVariable Long id) {
        return shipmentService.getShipmentById(id);
    }

    @PutMapping("/{id}")
    public Shipment update(
            @PathVariable Long id,
            @Valid @RequestBody Shipment shipment) {
        return shipmentService.updateShipment(id, shipment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        shipmentService.softDeleteShipment(id);
    }
}
