package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.DeliveryStop;
import com.codelegends.logistics_network.services.DeliveryStopService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-stops")
public class DeliveryStopController {

    private final DeliveryStopService deliveryStopService;

    public DeliveryStopController(DeliveryStopService deliveryStopService) {
        this.deliveryStopService = deliveryStopService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryStop create(@Valid @RequestBody DeliveryStop deliveryStop) {
        return deliveryStopService.createDeliveryStop(deliveryStop);
    }

    @GetMapping
    public List<DeliveryStop> getAll() {
        return deliveryStopService.getAllDeliveryStops();
    }

    @GetMapping("/{id}")
    public DeliveryStop getById(@PathVariable Long id) {
        return deliveryStopService.getDeliveryStopById(id);
    }

    @PutMapping("/{id}")
    public DeliveryStop update(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryStop deliveryStop) {
        return deliveryStopService.updateDeliveryStop(id, deliveryStop);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        deliveryStopService.softDeleteDeliveryStop(id);
    }
}
