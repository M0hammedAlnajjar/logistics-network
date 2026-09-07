package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Warehouse;
import com.codelegends.logistics_network.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        if (warehouse == null) {
            throw new IllegalArgumentException("Warehouse cannot be null");
        }

        if (warehouse.getName() == null || warehouse.getName().isBlank()) {
            throw new IllegalArgumentException("Warehouse name is required");
        }

        if (warehouse.getLocation() == null || warehouse.getLocation().isBlank()) {
            throw new IllegalArgumentException("Warehouse location is required");
        }

        if (warehouse.getCapacity() == null || warehouse.getCapacity() <= 0) {
            throw new IllegalArgumentException("Warehouse capacity must be greater than zero");
        }

        warehouse.setId(null);
        warehouse.setActive(true);

        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAllByIsActiveTrue();
    }

    public Warehouse getWarehouseById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Warehouse ID must be greater than zero");
        }

        return warehouseRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active warehouse not found with ID: " + id
                ));
    }

    public Warehouse updateWarehouse(Long id, Warehouse updatedWarehouse) {
        if (updatedWarehouse == null) {
            throw new IllegalArgumentException("Warehouse cannot be null");
        }

        if (updatedWarehouse.getName() == null || updatedWarehouse.getName().isBlank()) {
            throw new IllegalArgumentException("Warehouse name is required");
        }

        if (updatedWarehouse.getLocation() == null || updatedWarehouse.getLocation().isBlank()) {
            throw new IllegalArgumentException("Warehouse location is required");
        }

        if (updatedWarehouse.getCapacity() == null || updatedWarehouse.getCapacity() <= 0) {
            throw new IllegalArgumentException("Warehouse capacity must be greater than zero");
        }

        Warehouse warehouse = getWarehouseById(id);

        warehouse.setName(updatedWarehouse.getName());
        warehouse.setLocation(updatedWarehouse.getLocation());
        warehouse.setCapacity(updatedWarehouse.getCapacity());

        return warehouseRepository.save(warehouse);
    }

    public void softDeleteWarehouse(Long id) {
        Warehouse warehouse = getWarehouseById(id);
        warehouse.setActive(false);
        warehouseRepository.save(warehouse);
    }
}
