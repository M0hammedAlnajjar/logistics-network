package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Staff;
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
public class StaffDTO {

    private Long id;
    private String name;
    private String role;
    private String phoneNumber;
    private Long warehouseId;

    public static StaffDTO convertToDTO(Staff entity) {
        if (entity == null) {
            return null;
        }
        return StaffDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .role(entity.getRole())
                .phoneNumber(entity.getPhoneNumber())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .build();
    }

    public static List<StaffDTO> convertToDTO(List<Staff> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(StaffDTO::convertToDTO).toList();
    }

    public Staff toEntity() {
        Staff entity = new Staff();
        entity.setName(name);
        entity.setRole(role);
        entity.setPhoneNumber(phoneNumber);
        if (warehouseId != null) {
            Warehouse warehouse = new Warehouse();
            warehouse.setId(warehouseId);
            entity.setWarehouse(warehouse);
        }
        return entity;
    }
}
