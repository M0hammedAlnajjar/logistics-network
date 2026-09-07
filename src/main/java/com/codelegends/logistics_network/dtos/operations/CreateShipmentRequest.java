package com.codelegends.logistics_network.dtos.operations;

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

    private Long warehouseId;
    private Long customerId;
    private LocalDate shipmentDate;
    private List<ShipmentLineRequest> items;
}
