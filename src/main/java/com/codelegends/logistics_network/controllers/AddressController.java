package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.AddressDTO;
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
    public AddressDTO create(@Valid @RequestBody AddressDTO addressDTO) {
        return AddressDTO.convertToDTO(addressService.createAddress(addressDTO.toEntity()));
    }

    @GetMapping
    public List<AddressDTO> getAll() {
        return AddressDTO.convertToDTO(addressService.getAllAddresses());
    }

    @GetMapping("/{id}")
    public AddressDTO getById(@PathVariable Long id) {
        return AddressDTO.convertToDTO(addressService.getAddressById(id));
    }

    @PutMapping("/{id}")
    public AddressDTO update(
            @PathVariable Long id,
            @Valid @RequestBody AddressDTO addressDTO) {
        return AddressDTO.convertToDTO(
                addressService.updateAddress(id, addressDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        addressService.softDeleteAddress(id);
    }
}
