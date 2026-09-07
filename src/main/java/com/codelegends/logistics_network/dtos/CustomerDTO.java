package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.enums.CustomerType;

import com.codelegends.logistics_network.Entities.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Customer name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Customer email must be valid")
    @Size(max = 150, message = "Customer email must not exceed 150 characters")
    private String email;

    @NotBlank(message = "Customer phone number is required")
    @Size(max = 20, message = "Customer phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotNull(message = "Customer type is required")
    private CustomerType type;

    public static CustomerDTO convertToDTO(Customer entity) {
        if (entity == null) return null;
        return CustomerDTO.builder().id(entity.getId()).name(entity.getName())
                .email(entity.getEmail()).phoneNumber(entity.getPhoneNumber())
                .type(entity.getType()).build();
    }

    public static List<CustomerDTO> convertToDTO(List<Customer> entities) {
        return entities == null ? List.of()
                : entities.stream().map(CustomerDTO::convertToDTO).toList();
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
