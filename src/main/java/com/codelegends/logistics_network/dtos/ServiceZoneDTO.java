package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.ServiceZone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Service zone name is required")
    @Size(max = 100, message = "Service zone name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Service zone region is required")
    @Size(max = 100, message = "Service zone region must not exceed 100 characters")
    private String region;

    @NotNull(message = "Service zone base rate is required")
    @PositiveOrZero(message = "Service zone base rate cannot be negative")
    private BigDecimal baseRate;

    public static ServiceZoneDTO convertToDTO(ServiceZone entity) {
        if (entity == null) return null;
        return ServiceZoneDTO.builder().id(entity.getId()).name(entity.getName())
                .region(entity.getRegion()).baseRate(entity.getBaseRate()).build();
    }

    public static List<ServiceZoneDTO> convertToDTO(List<ServiceZone> entities) {
        return entities == null ? List.of()
                : entities.stream().map(ServiceZoneDTO::convertToDTO).toList();
    }

    public ServiceZone toEntity() {
        ServiceZone entity = new ServiceZone();
        entity.setName(name);
        entity.setRegion(region);
        entity.setBaseRate(baseRate);
        return entity;
    }
}
