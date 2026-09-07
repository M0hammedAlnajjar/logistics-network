package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.VehicleDTO;
import com.codelegends.logistics_network.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDTO create(@Valid @RequestBody VehicleDTO vehicleDTO) {
        return VehicleDTO.convertToDTO(vehicleService.createVehicle(vehicleDTO.toEntity()));
    }

    @GetMapping
    public List<VehicleDTO> getAll() {
        return VehicleDTO.convertToDTO(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public VehicleDTO getById(@PathVariable Long id) {
        return VehicleDTO.convertToDTO(vehicleService.getVehicleById(id));
    }

    @PutMapping("/{id}")
    public VehicleDTO update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleDTO vehicleDTO) {
        return VehicleDTO.convertToDTO(
                vehicleService.updateVehicle(id, vehicleDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vehicleService.softDeleteVehicle(id);
    }
}
