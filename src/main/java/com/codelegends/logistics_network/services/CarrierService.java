package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.repositories.CarrierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrierService {

    private final CarrierRepository carrierRepository;

    public CarrierService(CarrierRepository carrierRepository) {
        this.carrierRepository = carrierRepository;
    }

    public Carrier createCarrier(Carrier carrier) {
        validateCarrier(carrier);
        if (carrierRepository.existsByContactEmail(carrier.getContactEmail())) {
            throw new IllegalArgumentException("Carrier contact email already exists");
        }
        carrier.setId(null);
        carrier.setActive(true);
        return carrierRepository.save(carrier);
    }

    public List<Carrier> getAllCarriers() {
        return carrierRepository.findAllByIsActiveTrue();
    }

    public Carrier getCarrierById(Long id) {
        validateId(id);
        return carrierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active carrier not found with ID: " + id));
    }

    public Carrier updateCarrier(Long id, Carrier updatedCarrier) {
        validateCarrier(updatedCarrier);
        Carrier carrier = getCarrierById(id);
        if (carrierRepository.existsByContactEmailAndIdNot(
                updatedCarrier.getContactEmail(), id)) {
            throw new IllegalArgumentException("Carrier contact email already exists");
        }
        carrier.setName(updatedCarrier.getName());
        carrier.setContactEmail(updatedCarrier.getContactEmail());
        carrier.setPhoneNumber(updatedCarrier.getPhoneNumber());
        carrier.setCountry(updatedCarrier.getCountry());
        return carrierRepository.save(carrier);
    }

    public void softDeleteCarrier(Long id) {
        Carrier carrier = getCarrierById(id);
        carrier.setActive(false);
        carrierRepository.save(carrier);
    }

    private void validateCarrier(Carrier carrier) {
        if (carrier == null) {
            throw new IllegalArgumentException("Carrier cannot be null");
        }
        if (carrier.getName() == null || carrier.getName().isBlank()) {
            throw new IllegalArgumentException("Carrier name is required");
        }
        if (carrier.getContactEmail() == null || carrier.getContactEmail().isBlank()) {
            throw new IllegalArgumentException("Carrier contact email is required");
        }
        if (carrier.getPhoneNumber() == null || carrier.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Carrier phone number is required");
        }
        if (carrier.getCountry() == null || carrier.getCountry().isBlank()) {
            throw new IllegalArgumentException("Carrier country is required");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Carrier ID must be greater than zero");
        }
    }
}
