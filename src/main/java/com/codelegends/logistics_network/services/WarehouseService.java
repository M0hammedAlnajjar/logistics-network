package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }
}
