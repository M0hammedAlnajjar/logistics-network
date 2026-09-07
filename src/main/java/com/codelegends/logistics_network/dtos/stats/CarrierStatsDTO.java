package com.codelegends.logistics_network.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrierStatsDTO {

    private Long carrierId;
    private long vehicles;
    private long drivers;
    private long activeRoutes;
}
