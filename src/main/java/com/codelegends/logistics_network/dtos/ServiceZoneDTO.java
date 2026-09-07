package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.ServiceZone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceZoneDTO {

    private Long id;
    private String name;
    private String region;
    private BigDecimal baseRate;

    public static ServiceZoneDTO convertToDTO(ServiceZone entity) {
        if (entity == null) {
            return null;
        }
        return ServiceZoneDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .region(entity.getRegion())
                .baseRate(entity.getBaseRate())
                .build();
    }

    public static List<ServiceZoneDTO> convertToDTO(List<ServiceZone> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(ServiceZoneDTO::convertToDTO).toList();
    }

    public ServiceZone toEntity() {
        ServiceZone entity = new ServiceZone();
        entity.setName(name);
        entity.setRegion(region);
        entity.setBaseRate(baseRate);
        return entity;
    }
}
