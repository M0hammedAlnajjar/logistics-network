package com.codelegends.logistics_network.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseStatsDTO {

    private Long warehouseId;
    private long activeShipments;
    private long totalInventoryUnits;
}
