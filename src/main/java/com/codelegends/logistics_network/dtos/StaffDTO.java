package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.enums.StaffRole;

import com.codelegends.logistics_network.Entities.Staff;
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
public class StaffDTO {

    private Long id;

    @NotBlank(message = "Staff name is required")
    @Size(max = 100, message = "Staff name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Staff role is required")
    private StaffRole role;

    @NotBlank(message = "Staff phone number is required")
    @Size(max = 20, message = "Staff phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotNull(message = "Warehouse ID is required")
    @Positive(message = "Warehouse ID must be greater than zero")
    private Long warehouseId;

    public static StaffDTO convertToDTO(Staff entity) {
        if (entity == null) return null;
        return StaffDTO.builder().id(entity.getId()).name(entity.getName())
                .role(entity.getRole()).phoneNumber(entity.getPhoneNumber())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .build();
    }

    public static List<StaffDTO> convertToDTO(List<Staff> entities) {
        return entities == null ? List.of()
                : entities.stream().map(StaffDTO::convertToDTO).toList();
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
