package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Warehouse;
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
    private String name;
    private String location;
    private Integer capacity;

    public static WarehouseDTO convertToDTO(Warehouse entity) {
        if (entity == null) {
            return null;
        }
        return WarehouseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .capacity(entity.getCapacity())
                .build();
    }

    public static List<WarehouseDTO> convertToDTO(List<Warehouse> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(WarehouseDTO::convertToDTO).toList();
    }

    public Warehouse toEntity() {
        Warehouse entity = new Warehouse();
        entity.setName(name);
        entity.setLocation(location);
        entity.setCapacity(capacity);
        return entity;
    }
}
