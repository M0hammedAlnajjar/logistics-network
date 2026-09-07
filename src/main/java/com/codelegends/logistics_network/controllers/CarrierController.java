package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.Carrier;
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
    public Carrier create(@Valid @RequestBody Carrier carrier) {
        return carrierService.createCarrier(carrier);
    }

    @GetMapping
    public List<Carrier> getAll() {
        return carrierService.getAllCarriers();
    }

    @GetMapping("/{id}")
    public Carrier getById(@PathVariable Long id) {
        return carrierService.getCarrierById(id);
    }

    @PutMapping("/{id}")
    public Carrier update(
            @PathVariable Long id,
            @Valid @RequestBody Carrier carrier) {
        return carrierService.updateCarrier(id, carrier);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        carrierService.softDeleteCarrier(id);
    }
}
