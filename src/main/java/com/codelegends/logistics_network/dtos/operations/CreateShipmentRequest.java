package com.codelegends.logistics_network.dtos.operations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentRequest {

    @NotNull(message = "Warehouse ID is required")
    @Positive(message = "Warehouse ID must be greater than zero")
    private Long warehouseId;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be greater than zero")
    private Long customerId;

    @NotNull(message = "Shipment date is required")
    @FutureOrPresent(message = "Shipment date cannot be in the past")
    private LocalDate shipmentDate;

    @Valid
    @NotEmpty(message = "Shipment must contain at least one item")
    private List<ShipmentLineRequest> items;
}
