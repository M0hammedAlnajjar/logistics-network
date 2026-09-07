package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Warehouse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {

    private Long id;

    @NotBlank(message = "Warehouse name is required")
    @Size(max = 100, message = "Warehouse name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Warehouse location is required")
    @Size(max = 150, message = "Warehouse location must not exceed 150 characters")
    private String location;

    @NotNull(message = "Warehouse capacity is required")
    @Positive(message = "Warehouse capacity must be greater than zero")
    private Integer capacity;

    public static WarehouseDTO convertToDTO(Warehouse entity) {
        if (entity == null) return null;
        return WarehouseDTO.builder().id(entity.getId()).name(entity.getName())
                .location(entity.getLocation()).capacity(entity.getCapacity()).build();
    }

    public static List<WarehouseDTO> convertToDTO(List<Warehouse> entities) {
        return entities == null ? List.of()
                : entities.stream().map(WarehouseDTO::convertToDTO).toList();
    }

    public Warehouse toEntity() {
        Warehouse entity = new Warehouse();
        entity.setName(name);
        entity.setLocation(location);
        entity.setCapacity(capacity);
        return entity;
    }
}
