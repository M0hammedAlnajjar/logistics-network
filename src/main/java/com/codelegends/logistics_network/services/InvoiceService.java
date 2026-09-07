package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.Invoice;
import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.repositories.CustomerRepository;
import com.codelegends.logistics_network.repositories.InvoiceRepository;
import com.codelegends.logistics_network.repositories.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ShipmentRepository shipmentRepository;
    private final CustomerRepository customerRepository;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            ShipmentRepository shipmentRepository,
            CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.shipmentRepository = shipmentRepository;
        this.customerRepository = customerRepository;
    }

    public Invoice createInvoice(Invoice invoice) {
        validateInvoice(invoice);
        invoice.setId(null);
        invoice.setActive(true);
        invoice.setShipment(getShipment(invoice.getShipment()));
        invoice.setCustomer(getCustomer(invoice.getCustomer()));
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAllByIsActiveTrue();
    }

    public Invoice getInvoiceById(Long id) {
        validateId(id);
        return invoiceRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active invoice not found with ID: " + id));
    }

    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {
        validateInvoice(updatedInvoice);
        Invoice invoice = getInvoiceById(id);
        invoice.setAmount(updatedInvoice.getAmount());
        invoice.setStatus(updatedInvoice.getStatus());
        invoice.setIssuedDate(updatedInvoice.getIssuedDate());
        invoice.setShipment(getShipment(updatedInvoice.getShipment()));
        invoice.setCustomer(getCustomer(updatedInvoice.getCustomer()));
        return invoiceRepository.save(invoice);
    }

    public void softDeleteInvoice(Long id) {
        Invoice invoice = getInvoiceById(id);
        invoice.setActive(false);
        invoiceRepository.save(invoice);
    }

    private void validateInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        if (invoice.getAmount() == null
                || invoice.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invoice amount cannot be negative");
        }
        if (invoice.getStatus() == null) {
            throw new IllegalArgumentException("Invoice status is required");
        }
        if (invoice.getIssuedDate() == null) {
            throw new IllegalArgumentException("Invoice issued date is required");
        }
    }

    private Shipment getShipment(Shipment shipment) {
        if (shipment == null || shipment.getId() == null) {
            throw new IllegalArgumentException("Shipment ID is required");
        }
        return shipmentRepository.findByIdAndIsActiveTrue(shipment.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active shipment not found"));
    }

    private Customer getCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        return customerRepository.findByIdAndIsActiveTrue(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active customer not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invoice ID must be greater than zero");
        }
    }
}
