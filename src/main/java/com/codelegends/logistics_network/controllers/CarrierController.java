package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.CarrierDTO;
import com.codelegends.logistics_network.services.CarrierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carriers")
public class CarrierController {

    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarrierDTO create(@Valid @RequestBody CarrierDTO carrierDTO) {
        return CarrierDTO.convertToDTO(carrierService.createCarrier(carrierDTO.toEntity()));
    }

    @GetMapping
    public List<CarrierDTO> getAll() {
        return CarrierDTO.convertToDTO(carrierService.getAllCarriers());
    }

    @GetMapping("/{id}")
    public CarrierDTO getById(@PathVariable Long id) {
        return CarrierDTO.convertToDTO(carrierService.getCarrierById(id));
    }

    @PutMapping("/{id}")
    public CarrierDTO update(
            @PathVariable Long id,
            @Valid @RequestBody CarrierDTO carrierDTO) {
        return CarrierDTO.convertToDTO(
                carrierService.updateCarrier(id, carrierDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        carrierService.softDeleteCarrier(id);
    }
}
