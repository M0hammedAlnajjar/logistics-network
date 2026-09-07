package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String type;

    public static CustomerDTO convertToDTO(Customer entity) {
        if (entity == null) {
            return null;
        }
        return CustomerDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .type(entity.getType())
                .build();
    }

    public static List<CustomerDTO> convertToDTO(List<Customer> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(CustomerDTO::convertToDTO).toList();
    }

    public Customer toEntity() {
        Customer entity = new Customer();
        entity.setName(name);
        entity.setEmail(email);
        entity.setPhoneNumber(phoneNumber);
        entity.setType(type);
        return entity;
    }
}
