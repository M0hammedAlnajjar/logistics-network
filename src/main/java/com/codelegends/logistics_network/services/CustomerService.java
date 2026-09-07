package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        validateCustomer(customer);
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Customer email already exists");
        }
        customer.setId(null);
        customer.setActive(true);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAllByIsActiveTrue();
    }

    public Customer getCustomerById(Long id) {
        validateId(id);
        return customerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active customer not found with ID: " + id));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        validateCustomer(updatedCustomer);
        Customer customer = getCustomerById(id);
        if (customerRepository.existsByEmailAndIdNot(updatedCustomer.getEmail(), id)) {
            throw new IllegalArgumentException("Customer email already exists");
        }
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        customer.setType(updatedCustomer.getType());
        return customerRepository.save(customer);
    }

    public void softDeleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customer.setActive(false);
        customerRepository.save(customer);
    }

    private void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new IllegalArgumentException("Customer email is required");
        }
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Customer phone number is required");
        }
        if (customer.getType() == null || customer.getType().isBlank()) {
            throw new IllegalArgumentException("Customer type is required");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Customer ID must be greater than zero");
        }
    }
}
