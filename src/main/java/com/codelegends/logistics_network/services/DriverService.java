package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Driver;
import com.codelegends.logistics_network.repositories.CarrierRepository;
import com.codelegends.logistics_network.repositories.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final CarrierRepository carrierRepository;

    public DriverService(
            DriverRepository driverRepository,
            CarrierRepository carrierRepository) {
        this.driverRepository = driverRepository;
        this.carrierRepository = carrierRepository;
    }

    public Driver createDriver(Driver driver) {
        validateDriver(driver);
        if (driverRepository.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new IllegalArgumentException("Driver license number already exists");
        }
        driver.setId(null);
        driver.setActive(true);
        driver.setCarrier(getCarrier(driver.getCarrier()));
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAllByIsActiveTrue();
    }

    public Driver getDriverById(Long id) {
        validateId(id);
        return driverRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active driver not found with ID: " + id));
    }

    public Driver updateDriver(Long id, Driver updatedDriver) {
        validateDriver(updatedDriver);
        Driver driver = getDriverById(id);
        if (driverRepository.existsByLicenseNumberAndIdNot(
                updatedDriver.getLicenseNumber(), id)) {
            throw new IllegalArgumentException("Driver license number already exists");
        }
        driver.setName(updatedDriver.getName());
        driver.setLicenseNumber(updatedDriver.getLicenseNumber());
        driver.setPhoneNumber(updatedDriver.getPhoneNumber());
        driver.setStatus(updatedDriver.getStatus());
        driver.setCarrier(getCarrier(updatedDriver.getCarrier()));
        return driverRepository.save(driver);
    }

    public void softDeleteDriver(Long id) {
        Driver driver = getDriverById(id);
        driver.setActive(false);
        driverRepository.save(driver);
    }

    private void validateDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        if (driver.getName() == null || driver.getName().isBlank()) {
            throw new IllegalArgumentException("Driver name is required");
        }
        if (driver.getLicenseNumber() == null || driver.getLicenseNumber().isBlank()) {
            throw new IllegalArgumentException("Driver license number is required");
        }
        if (driver.getPhoneNumber() == null || driver.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Driver phone number is required");
        }
        if (driver.getStatus() == null || driver.getStatus().isBlank()) {
            throw new IllegalArgumentException("Driver status is required");
        }
    }

    private Carrier getCarrier(Carrier carrier) {
        if (carrier == null || carrier.getId() == null) {
            throw new IllegalArgumentException("Carrier ID is required");
        }
        return carrierRepository.findByIdAndIsActiveTrue(carrier.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active carrier not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Driver ID must be greater than zero");
        }
    }
}
