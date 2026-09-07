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

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddDeliveryStopRequest {

    @NotNull(message = "Shipment ID is required")
    @Positive(message = "Shipment ID must be greater than zero")
    private Long shipmentId;

    @NotNull(message = "Delivery stop sequence is required")
    @Positive(message = "Delivery stop sequence must be greater than zero")
    private Integer sequence;

    @NotBlank(message = "Delivery stop address is required")
    @Size(max = 250, message = "Delivery stop address must not exceed 250 characters")
    private String address;

    @NotNull(message = "Delivery stop ETA is required")
    @FutureOrPresent(message = "Delivery stop ETA cannot be in the past")
    private LocalDateTime eta;
}
