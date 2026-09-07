package com.codelegends.logistics_network.dtos.operations;

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

    private BigDecimal amount;
    private LocalDate issuedDate;
}
