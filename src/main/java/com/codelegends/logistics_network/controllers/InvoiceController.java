package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.InvoiceDTO;
import com.codelegends.logistics_network.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceDTO create(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        return InvoiceDTO.convertToDTO(invoiceService.createInvoice(invoiceDTO.toEntity()));
    }

    @GetMapping
    public List<InvoiceDTO> getAll() {
        return InvoiceDTO.convertToDTO(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public InvoiceDTO getById(@PathVariable Long id) {
        return InvoiceDTO.convertToDTO(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/{id}")
    public InvoiceDTO update(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceDTO invoiceDTO) {
        return InvoiceDTO.convertToDTO(
                invoiceService.updateInvoice(id, invoiceDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        invoiceService.softDeleteInvoice(id);
    }
}
