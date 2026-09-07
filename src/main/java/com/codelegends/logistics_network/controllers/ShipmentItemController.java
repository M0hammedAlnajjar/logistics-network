package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.ShipmentItemDTO;
import com.codelegends.logistics_network.services.ShipmentItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipment-items")
public class ShipmentItemController {

    private final ShipmentItemService shipmentItemService;

    public ShipmentItemController(ShipmentItemService shipmentItemService) {
        this.shipmentItemService = shipmentItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentItemDTO create(@Valid @RequestBody ShipmentItemDTO shipmentItemDTO) {
        return ShipmentItemDTO.convertToDTO(shipmentItemService.createShipmentItem(shipmentItemDTO.toEntity()));
    }

    @GetMapping
    public List<ShipmentItemDTO> getAll() {
        return ShipmentItemDTO.convertToDTO(shipmentItemService.getAllShipmentItems());
    }

    @GetMapping("/{id}")
    public ShipmentItemDTO getById(@PathVariable Long id) {
        return ShipmentItemDTO.convertToDTO(shipmentItemService.getShipmentItemById(id));
    }

    @PutMapping("/{id}")
    public ShipmentItemDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ShipmentItemDTO shipmentItemDTO) {
        return ShipmentItemDTO.convertToDTO(
                shipmentItemService.updateShipmentItem(id, shipmentItemDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        shipmentItemService.softDeleteShipmentItem(id);
    }
}
