package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Carrier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CarrierDTO {

    private Long id;

    @NotBlank(message = "Carrier name is required")
    @Size(max = 100, message = "Carrier name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Carrier contact email is required")
    @Email(message = "Carrier contact email must be valid")
    @Size(max = 150, message = "Carrier contact email must not exceed 150 characters")
    private String contactEmail;

    @NotBlank(message = "Carrier phone number is required")
    @Size(max = 20, message = "Carrier phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Carrier country is required")
    @Size(max = 100, message = "Carrier country must not exceed 100 characters")
    private String country;

    public static CarrierDTO convertToDTO(Carrier entity) {
        if (entity == null) return null;
        return CarrierDTO.builder().id(entity.getId()).name(entity.getName())
                .contactEmail(entity.getContactEmail())
                .phoneNumber(entity.getPhoneNumber())
                .country(entity.getCountry()).build();
    }

    public static List<CarrierDTO> convertToDTO(List<Carrier> entities) {
        return entities == null ? List.of()
                : entities.stream().map(CarrierDTO::convertToDTO).toList();
    }

    public Carrier toEntity() {
        Carrier entity = new Carrier();
        entity.setName(name);
        entity.setContactEmail(contactEmail);
        entity.setPhoneNumber(phoneNumber);
        entity.setCountry(country);
        return entity;
    }
}
