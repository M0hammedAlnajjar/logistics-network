package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.WarehouseDTO;
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
    public WarehouseDTO create(@Valid @RequestBody WarehouseDTO warehouseDTO) {
        return WarehouseDTO.convertToDTO(warehouseService.createWarehouse(warehouseDTO.toEntity()));
    }

    @GetMapping
    public List<WarehouseDTO> getAll() {
        return WarehouseDTO.convertToDTO(warehouseService.getAllWarehouses());
    }

    @GetMapping("/{id}")
    public WarehouseDTO getById(@PathVariable Long id) {
        return WarehouseDTO.convertToDTO(warehouseService.getWarehouseById(id));
    }

    @PutMapping("/{id}")
    public WarehouseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseDTO warehouseDTO) {
        return WarehouseDTO.convertToDTO(
                warehouseService.updateWarehouse(id, warehouseDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        warehouseService.softDeleteWarehouse(id);
    }
}
