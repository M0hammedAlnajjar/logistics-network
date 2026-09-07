package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.ShipmentDTO;
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
    public ShipmentDTO create(@Valid @RequestBody ShipmentDTO shipmentDTO) {
        return ShipmentDTO.convertToDTO(shipmentService.createShipment(shipmentDTO.toEntity()));
    }

    @GetMapping
    public List<ShipmentDTO> getAll() {
        return ShipmentDTO.convertToDTO(shipmentService.getAllShipments());
    }

    @GetMapping("/{id}")
    public ShipmentDTO getById(@PathVariable Long id) {
        return ShipmentDTO.convertToDTO(shipmentService.getShipmentById(id));
    }

    @PutMapping("/{id}")
    public ShipmentDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ShipmentDTO shipmentDTO) {
        return ShipmentDTO.convertToDTO(
                shipmentService.updateShipment(id, shipmentDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        shipmentService.softDeleteShipment(id);
    }
}
