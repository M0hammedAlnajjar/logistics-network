package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.ShipmentItem;
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
    public ShipmentItem create(@Valid @RequestBody ShipmentItem shipmentItem) {
        return shipmentItemService.createShipmentItem(shipmentItem);
    }

    @GetMapping
    public List<ShipmentItem> getAll() {
        return shipmentItemService.getAllShipmentItems();
    }

    @GetMapping("/{id}")
    public ShipmentItem getById(@PathVariable Long id) {
        return shipmentItemService.getShipmentItemById(id);
    }

    @PutMapping("/{id}")
    public ShipmentItem update(
            @PathVariable Long id,
            @Valid @RequestBody ShipmentItem shipmentItem) {
        return shipmentItemService.updateShipmentItem(id, shipmentItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        shipmentItemService.softDeleteShipmentItem(id);
    }
}
