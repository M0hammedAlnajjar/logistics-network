package com.codelegends.logistics_network.dtos.operations;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateInvoiceRequest {

    @NotNull(message = "Invoice amount is required")
    @Positive(message = "Invoice amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Invoice issued date is required")
    @PastOrPresent(message = "Invoice issued date cannot be in the future")
    private LocalDate issuedDate;
}
