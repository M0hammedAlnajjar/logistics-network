package com.codelegends.logistics_network.dtos.operations;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignCarrierRequest {

    @NotNull(message = "Carrier ID is required")
    @Positive(message = "Carrier ID must be greater than zero")
    private Long carrierId;
}
