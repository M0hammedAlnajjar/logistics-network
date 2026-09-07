package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.enums.VehicleStatus;
import com.codelegends.logistics_network.enums.VehicleType;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class VehicleDTO {

    private Long id;

    @NotBlank(message = "Vehicle plate number is required")
    @Size(max = 30, message = "Vehicle plate number must not exceed 30 characters")
    private String plateNumber;

    @NotNull(message = "Vehicle type is required")
    private VehicleType type;

    @NotNull(message = "Vehicle capacity is required")
    @Positive(message = "Vehicle capacity must be greater than zero")
    private BigDecimal capacityKg;

    @NotNull(message = "Vehicle status is required")
    private VehicleStatus status;

    @NotNull(message = "Carrier ID is required")
    @Positive(message = "Carrier ID must be greater than zero")
    private Long carrierId;

    public static VehicleDTO convertToDTO(Vehicle entity) {
        if (entity == null) return null;
        return VehicleDTO.builder().id(entity.getId())
                .plateNumber(entity.getPlateNumber()).type(entity.getType())
                .capacityKg(entity.getCapacityKg()).status(entity.getStatus())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .build();
    }

    public static List<VehicleDTO> convertToDTO(List<Vehicle> entities) {
        return entities == null ? List.of()
                : entities.stream().map(VehicleDTO::convertToDTO).toList();
    }

    public Vehicle toEntity() {
        Vehicle entity = new Vehicle();
        entity.setPlateNumber(plateNumber);
        entity.setType(type);
        entity.setCapacityKg(capacityKg);
        entity.setStatus(status);
        if (carrierId != null) {
            Carrier carrier = new Carrier();
            carrier.setId(carrierId);
            entity.setCarrier(carrier);
        }
        return entity;
    }
}
