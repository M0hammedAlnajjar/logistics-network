package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Address;
import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.ServiceZone;
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
public class AddressDTO {

    private Long id;

    @NotBlank(message = "Street is required")
    @Size(max = 200, message = "Street must not exceed 200 characters")
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @NotBlank(message = "Postal code is required")
    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be greater than zero")
    private Long customerId;

    @Positive(message = "Service zone ID must be greater than zero")
    private Long serviceZoneId;

    public static AddressDTO convertToDTO(Address entity) {
        if (entity == null) return null;
        return AddressDTO.builder().id(entity.getId()).street(entity.getStreet())
                .city(entity.getCity()).postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .serviceZoneId(entity.getServiceZone() == null ? null : entity.getServiceZone().getId())
                .build();
    }

    public static List<AddressDTO> convertToDTO(List<Address> entities) {
        return entities == null ? List.of()
                : entities.stream().map(AddressDTO::convertToDTO).toList();
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
            ServiceZone serviceZone = new ServiceZone();
            serviceZone.setId(serviceZoneId);
            entity.setServiceZone(serviceZone);
        }
        return entity;
    }
}
