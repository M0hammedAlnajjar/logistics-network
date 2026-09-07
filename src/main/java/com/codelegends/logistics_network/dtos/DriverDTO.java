package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private Long id;
    private String name;
    private String licenseNumber;
    private String phoneNumber;
    private String status;
    private Long carrierId;

    public static DriverDTO convertToDTO(Driver entity) {
        if (entity == null) {
            return null;
        }
        return DriverDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .licenseNumber(entity.getLicenseNumber())
                .phoneNumber(entity.getPhoneNumber())
                .status(entity.getStatus())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .build();
    }

    public static List<DriverDTO> convertToDTO(List<Driver> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(DriverDTO::convertToDTO).toList();
    }

    public Driver toEntity() {
        Driver entity = new Driver();
        entity.setName(name);
        entity.setLicenseNumber(licenseNumber);
        entity.setPhoneNumber(phoneNumber);
        entity.setStatus(status);
        if (carrierId != null) {
            Carrier carrier = new Carrier();
            carrier.setId(carrierId);
            entity.setCarrier(carrier);
        }
        return entity;
    }
}
