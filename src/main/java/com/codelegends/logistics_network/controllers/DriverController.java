package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.Driver;
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
    public Driver create(@Valid @RequestBody Driver driver) {
        return driverService.createDriver(driver);
    }

    @GetMapping
    public List<Driver> getAll() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public Driver getById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @PutMapping("/{id}")
    public Driver update(
            @PathVariable Long id,
            @Valid @RequestBody Driver driver) {
        return driverService.updateDriver(id, driver);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        driverService.softDeleteDriver(id);
    }
}
