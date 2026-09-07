package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Vehicle;
import com.codelegends.logistics_network.repositories.CarrierRepository;
import com.codelegends.logistics_network.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CarrierRepository carrierRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            CarrierRepository carrierRepository) {
        this.vehicleRepository = vehicleRepository;
        this.carrierRepository = carrierRepository;
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        validateVehicle(vehicle);
        if (vehicleRepository.existsByPlateNumber(vehicle.getPlateNumber())) {
            throw new IllegalArgumentException("Vehicle plate number already exists");
        }
        vehicle.setId(null);
        vehicle.setActive(true);
        vehicle.setCarrier(getCarrier(vehicle.getCarrier()));
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAllByIsActiveTrue();
    }

    public Vehicle getVehicleById(Long id) {
        validateId(id);
        return vehicleRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active vehicle not found with ID: " + id));
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        validateVehicle(updatedVehicle);
        Vehicle vehicle = getVehicleById(id);
        if (vehicleRepository.existsByPlateNumberAndIdNot(
                updatedVehicle.getPlateNumber(), id)) {
            throw new IllegalArgumentException("Vehicle plate number already exists");
        }
        vehicle.setPlateNumber(updatedVehicle.getPlateNumber());
        vehicle.setType(updatedVehicle.getType());
        vehicle.setCapacityKg(updatedVehicle.getCapacityKg());
        vehicle.setStatus(updatedVehicle.getStatus());
        vehicle.setCarrier(getCarrier(updatedVehicle.getCarrier()));
        return vehicleRepository.save(vehicle);
    }

    public void softDeleteVehicle(Long id) {
        Vehicle vehicle = getVehicleById(id);
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }

    private void validateVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (vehicle.getPlateNumber() == null || vehicle.getPlateNumber().isBlank()) {
            throw new IllegalArgumentException("Vehicle plate number is required");
        }
        if (vehicle.getType() == null) {
            throw new IllegalArgumentException("Vehicle type is required");
        }
        if (vehicle.getCapacityKg() == null
                || vehicle.getCapacityKg().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Vehicle capacity must be greater than zero");
        }
        if (vehicle.getStatus() == null) {
            throw new IllegalArgumentException("Vehicle status is required");
        }
    }

    private Carrier getCarrier(Carrier carrier) {
        if (carrier == null || carrier.getId() == null) {
            throw new IllegalArgumentException("Carrier ID is required");
        }
        return carrierRepository.findByIdAndIsActiveTrue(carrier.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active carrier not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Vehicle ID must be greater than zero");
        }
    }
}
