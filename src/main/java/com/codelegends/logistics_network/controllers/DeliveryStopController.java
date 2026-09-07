package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.DeliveryStopDTO;
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
    public DeliveryStopDTO create(@Valid @RequestBody DeliveryStopDTO deliveryStopDTO) {
        return DeliveryStopDTO.convertToDTO(deliveryStopService.createDeliveryStop(deliveryStopDTO.toEntity()));
    }

    @GetMapping
    public List<DeliveryStopDTO> getAll() {
        return DeliveryStopDTO.convertToDTO(deliveryStopService.getAllDeliveryStops());
    }

    @GetMapping("/{id}")
    public DeliveryStopDTO getById(@PathVariable Long id) {
        return DeliveryStopDTO.convertToDTO(deliveryStopService.getDeliveryStopById(id));
    }

    @PutMapping("/{id}")
    public DeliveryStopDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryStopDTO deliveryStopDTO) {
        return DeliveryStopDTO.convertToDTO(
                deliveryStopService.updateDeliveryStop(id, deliveryStopDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        deliveryStopService.softDeleteDeliveryStop(id);
    }
}
