package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Vehicle;
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
    private String plateNumber;
    private String type;
    private BigDecimal capacityKg;
    private String status;
    private Long carrierId;

    public static VehicleDTO convertToDTO(Vehicle entity) {
        if (entity == null) {
            return null;
        }
        return VehicleDTO.builder()
                .id(entity.getId())
                .plateNumber(entity.getPlateNumber())
                .type(entity.getType())
                .capacityKg(entity.getCapacityKg())
                .status(entity.getStatus())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .build();
    }

    public static List<VehicleDTO> convertToDTO(List<Vehicle> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(VehicleDTO::convertToDTO).toList();
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
