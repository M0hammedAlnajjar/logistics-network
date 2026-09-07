package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.ServiceZone;
import com.codelegends.logistics_network.repositories.ServiceZoneRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceZoneService {

    private final ServiceZoneRepository serviceZoneRepository;

    public ServiceZoneService(ServiceZoneRepository serviceZoneRepository) {
        this.serviceZoneRepository = serviceZoneRepository;
    }

    public ServiceZone createServiceZone(ServiceZone serviceZone) {
        validateServiceZone(serviceZone);
        if (serviceZoneRepository.existsByName(serviceZone.getName())) {
            throw new IllegalArgumentException("Service zone name already exists");
        }
        serviceZone.setId(null);
        serviceZone.setActive(true);
        return serviceZoneRepository.save(serviceZone);
    }

    public List<ServiceZone> getAllServiceZones() {
        return serviceZoneRepository.findAllByIsActiveTrue();
    }

    public ServiceZone getServiceZoneById(Long id) {
        validateId(id);
        return serviceZoneRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active service zone not found with ID: " + id));
    }

    public ServiceZone updateServiceZone(Long id, ServiceZone updatedServiceZone) {
        validateServiceZone(updatedServiceZone);
        ServiceZone serviceZone = getServiceZoneById(id);
        if (serviceZoneRepository.existsByNameAndIdNot(updatedServiceZone.getName(), id)) {
            throw new IllegalArgumentException("Service zone name already exists");
        }
        serviceZone.setName(updatedServiceZone.getName());
        serviceZone.setRegion(updatedServiceZone.getRegion());
        serviceZone.setBaseRate(updatedServiceZone.getBaseRate());
        return serviceZoneRepository.save(serviceZone);
    }

    public void softDeleteServiceZone(Long id) {
        ServiceZone serviceZone = getServiceZoneById(id);
        serviceZone.setActive(false);
        serviceZoneRepository.save(serviceZone);
    }

    private void validateServiceZone(ServiceZone serviceZone) {
        if (serviceZone == null) {
            throw new IllegalArgumentException("Service zone cannot be null");
        }
        if (serviceZone.getName() == null || serviceZone.getName().isBlank()) {
            throw new IllegalArgumentException("Service zone name is required");
        }
        if (serviceZone.getRegion() == null || serviceZone.getRegion().isBlank()) {
            throw new IllegalArgumentException("Service zone region is required");
        }
        if (serviceZone.getBaseRate() == null
                || serviceZone.getBaseRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Service zone base rate cannot be negative");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Service zone ID must be greater than zero");
        }
    }
}
