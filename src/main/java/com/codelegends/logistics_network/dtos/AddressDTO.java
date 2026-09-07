package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Address;
import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.ServiceZone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private Long customerId;
    private Long serviceZoneId;

    public static AddressDTO convertToDTO(Address entity) {
        if (entity == null) {
            return null;
        }
        return AddressDTO.builder()
                .id(entity.getId())
                .street(entity.getStreet())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .serviceZoneId(entity.getServiceZone() == null ? null : entity.getServiceZone().getId())
                .build();
    }

    public static List<AddressDTO> convertToDTO(List<Address> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(AddressDTO::convertToDTO).toList();
    }

    public Address toEntity() {
        Address entity = new Address();
        entity.setStreet(street);
        entity.setCity(city);
        entity.setPostalCode(postalCode);
        entity.setCountry(country);
        if (customerId != null) {
            Customer customer = new Customer();
            customer.setId(customerId);
            entity.setCustomer(customer);
        }
        if (serviceZoneId != null) {
            ServiceZone zone = new ServiceZone();
            zone.setId(serviceZoneId);
            entity.setServiceZone(zone);
        }
        return entity;
    }
}
