package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Carrier;
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
    private String name;
    private String contactEmail;
    private String phoneNumber;
    private String country;

    public static CarrierDTO convertToDTO(Carrier entity) {
        if (entity == null) {
            return null;
        }
        return CarrierDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .contactEmail(entity.getContactEmail())
                .phoneNumber(entity.getPhoneNumber())
                .country(entity.getCountry())
                .build();
    }

    public static List<CarrierDTO> convertToDTO(List<Carrier> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(CarrierDTO::convertToDTO).toList();
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
