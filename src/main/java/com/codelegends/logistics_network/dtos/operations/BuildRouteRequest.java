package com.codelegends.logistics_network.dtos.operations;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildRouteRequest {

    @NotNull(message = "Shipment ID is required")
    @Positive(message = "Shipment ID must be greater than zero")
    private Long shipmentId;

    @NotNull(message = "Vehicle ID is required")
    @Positive(message = "Vehicle ID must be greater than zero")
    private Long vehicleId;

    @NotNull(message = "Driver ID is required")
    @Positive(message = "Driver ID must be greater than zero")
    private Long driverId;

    @NotNull(message = "Route date is required")
    @FutureOrPresent(message = "Route date cannot be in the past")
    private LocalDate routeDate;

    @NotBlank(message = "Route origin is required")
    @Size(max = 150, message = "Route origin must not exceed 150 characters")
    private String origin;

    @NotBlank(message = "Route destination is required")
    @Size(max = 150, message = "Route destination must not exceed 150 characters")
    private String destination;
}
