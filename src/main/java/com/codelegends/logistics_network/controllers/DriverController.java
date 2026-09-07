package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.DriverDTO;
import com.codelegends.logistics_network.services.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO create(@Valid @RequestBody DriverDTO driverDTO) {
        return DriverDTO.convertToDTO(driverService.createDriver(driverDTO.toEntity()));
    }

    @GetMapping
    public List<DriverDTO> getAll() {
        return DriverDTO.convertToDTO(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public DriverDTO getById(@PathVariable Long id) {
        return DriverDTO.convertToDTO(driverService.getDriverById(id));
    }

    @PutMapping("/{id}")
    public DriverDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DriverDTO driverDTO) {
        return DriverDTO.convertToDTO(
                driverService.updateDriver(id, driverDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        driverService.softDeleteDriver(id);
    }
}
