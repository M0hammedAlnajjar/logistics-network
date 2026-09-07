package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.Address;
import com.codelegends.logistics_network.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Address create(@Valid @RequestBody Address address) {
        return addressService.createAddress(address);
    }

    @GetMapping
    public List<Address> getAll() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public Address getById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PutMapping("/{id}")
    public Address update(
            @PathVariable Long id,
            @Valid @RequestBody Address address) {
        return addressService.updateAddress(id, address);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        addressService.softDeleteAddress(id);
    }
}
