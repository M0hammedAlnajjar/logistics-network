package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.ServiceZoneDTO;
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
    public ServiceZoneDTO create(@Valid @RequestBody ServiceZoneDTO serviceZoneDTO) {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.createServiceZone(serviceZoneDTO.toEntity()));
    }

    @GetMapping
    public List<ServiceZoneDTO> getAll() {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.getAllServiceZones());
    }

    @GetMapping("/{id}")
    public ServiceZoneDTO getById(@PathVariable Long id) {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.getServiceZoneById(id));
    }

    @PutMapping("/{id}")
    public ServiceZoneDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ServiceZoneDTO serviceZoneDTO) {
        return ServiceZoneDTO.convertToDTO(
                serviceZoneService.updateServiceZone(id, serviceZoneDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        serviceZoneService.softDeleteServiceZone(id);
    }
}
