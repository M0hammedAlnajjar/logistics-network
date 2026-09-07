package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.CustomerDTO;
import com.codelegends.logistics_network.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO create(@Valid @RequestBody CustomerDTO customerDTO) {
        return CustomerDTO.convertToDTO(customerService.createCustomer(customerDTO.toEntity()));
    }

    @GetMapping
    public List<CustomerDTO> getAll() {
        return CustomerDTO.convertToDTO(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return CustomerDTO.convertToDTO(customerService.getCustomerById(id));
    }

    @PutMapping("/{id}")
    public CustomerDTO update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        return CustomerDTO.convertToDTO(
                customerService.updateCustomer(id, customerDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerService.softDeleteCustomer(id);
    }
}
