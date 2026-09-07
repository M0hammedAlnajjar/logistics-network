package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.Warehouse;
import com.codelegends.logistics_network.services.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Warehouse create(@Valid @RequestBody Warehouse warehouse) {
        return warehouseService.createWarehouse(warehouse);
    }

    @GetMapping
    public List<Warehouse> getAll() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}")
    public Warehouse getById(@PathVariable Long id) {
        return warehouseService.getWarehouseById(id);
    }

    @PutMapping("/{id}")
    public Warehouse update(
            @PathVariable Long id,
            @Valid @RequestBody Warehouse warehouse) {
        return warehouseService.updateWarehouse(id, warehouse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        warehouseService.softDeleteWarehouse(id);
    }
}
