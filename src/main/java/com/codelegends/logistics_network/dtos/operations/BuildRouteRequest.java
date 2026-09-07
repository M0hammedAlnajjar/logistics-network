package com.codelegends.logistics_network.dtos.operations;

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

    private Long shipmentId;
    private Long vehicleId;
    private Long driverId;
    private LocalDate routeDate;
    private String origin;
    private String destination;
}
