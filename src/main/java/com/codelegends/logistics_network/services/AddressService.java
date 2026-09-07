package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Address;
import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.ServiceZone;
import com.codelegends.logistics_network.repositories.AddressRepository;
import com.codelegends.logistics_network.repositories.CustomerRepository;
import com.codelegends.logistics_network.repositories.ServiceZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final ServiceZoneRepository serviceZoneRepository;

    public AddressService(
            AddressRepository addressRepository,
            CustomerRepository customerRepository,
            ServiceZoneRepository serviceZoneRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.serviceZoneRepository = serviceZoneRepository;
    }

    public Address createAddress(Address address) {
        validateAddress(address);
        address.setId(null);
        address.setActive(true);
        address.setCustomer(getCustomer(address.getCustomer()));
        address.setServiceZone(getOptionalServiceZone(address.getServiceZone()));
        return addressRepository.save(address);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAllByIsActiveTrue();
    }

    public Address getAddressById(Long id) {
        validateId(id);
        return addressRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active address not found with ID: " + id));
    }

    public Address updateAddress(Long id, Address updatedAddress) {
        validateAddress(updatedAddress);
        Address address = getAddressById(id);
        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setPostalCode(updatedAddress.getPostalCode());
        address.setCountry(updatedAddress.getCountry());
        address.setCustomer(getCustomer(updatedAddress.getCustomer()));
        address.setServiceZone(getOptionalServiceZone(updatedAddress.getServiceZone()));
        return addressRepository.save(address);
    }

    public void softDeleteAddress(Long id) {
        Address address = getAddressById(id);
        address.setActive(false);
        addressRepository.save(address);
    }

    private void validateAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (address.getStreet() == null || address.getStreet().isBlank()) {
            throw new IllegalArgumentException("Street is required");
        }
        if (address.getCity() == null || address.getCity().isBlank()) {
            throw new IllegalArgumentException("City is required");
        }
        if (address.getPostalCode() == null || address.getPostalCode().isBlank()) {
            throw new IllegalArgumentException("Postal code is required");
        }
        if (address.getCountry() == null || address.getCountry().isBlank()) {
            throw new IllegalArgumentException("Country is required");
        }
    }

    private Customer getCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        return customerRepository.findByIdAndIsActiveTrue(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active customer not found"));
    }

    private ServiceZone getOptionalServiceZone(ServiceZone serviceZone) {
        if (serviceZone == null) {
            return null;
        }
        if (serviceZone.getId() == null) {
            throw new IllegalArgumentException("Service zone ID is required");
        }
        return serviceZoneRepository.findByIdAndIsActiveTrue(serviceZone.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active service zone not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Address ID must be greater than zero");
        }
    }
}
