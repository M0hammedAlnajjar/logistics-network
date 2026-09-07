package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.ServiceZone;
import com.codelegends.logistics_network.services.ServiceZoneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-zones")
public class ServiceZoneController {

    private final ServiceZoneService serviceZoneService;

    public ServiceZoneController(ServiceZoneService serviceZoneService) {
        this.serviceZoneService = serviceZoneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceZone create(@Valid @RequestBody ServiceZone serviceZone) {
        return serviceZoneService.createServiceZone(serviceZone);
    }

    @GetMapping
    public List<ServiceZone> getAll() {
        return serviceZoneService.getAllServiceZones();
    }

    @GetMapping("/{id}")
    public ServiceZone getById(@PathVariable Long id) {
        return serviceZoneService.getServiceZoneById(id);
    }

    @PutMapping("/{id}")
    public ServiceZone update(
            @PathVariable Long id,
            @Valid @RequestBody ServiceZone serviceZone) {
        return serviceZoneService.updateServiceZone(id, serviceZone);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        serviceZoneService.softDeleteServiceZone(id);
    }
}
