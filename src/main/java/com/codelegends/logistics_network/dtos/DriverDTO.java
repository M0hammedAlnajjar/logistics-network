package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Driver;
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
public class DriverDTO {

    private Long id;

    @NotBlank(message = "Driver name is required")
    @Size(max = 100, message = "Driver name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Driver license number is required")
    @Size(max = 50, message = "Driver license number must not exceed 50 characters")
    private String licenseNumber;

    @NotBlank(message = "Driver phone number is required")
    @Size(max = 20, message = "Driver phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Driver status is required")
    @Size(max = 50, message = "Driver status must not exceed 50 characters")
    private String status;

    @NotNull(message = "Carrier ID is required")
    @Positive(message = "Carrier ID must be greater than zero")
    private Long carrierId;

    public static DriverDTO convertToDTO(Driver entity) {
        if (entity == null) return null;
        return DriverDTO.builder().id(entity.getId()).name(entity.getName())
                .licenseNumber(entity.getLicenseNumber())
                .phoneNumber(entity.getPhoneNumber()).status(entity.getStatus())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .build();
    }

    public static List<DriverDTO> convertToDTO(List<Driver> entities) {
        return entities == null ? List.of()
                : entities.stream().map(DriverDTO::convertToDTO).toList();
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
