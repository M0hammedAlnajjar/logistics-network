package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.Invoice;
import com.codelegends.logistics_network.Entities.Shipment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long id;

    @NotNull(message = "Invoice amount is required")
    @PositiveOrZero(message = "Invoice amount cannot be negative")
    private BigDecimal amount;

    @NotBlank(message = "Invoice status is required")
    @Size(max = 50, message = "Invoice status must not exceed 50 characters")
    private String status;

    @NotNull(message = "Invoice issued date is required")
    @PastOrPresent(message = "Invoice issued date cannot be in the future")
    private LocalDate issuedDate;

    @NotNull(message = "Shipment ID is required")
    @Positive(message = "Shipment ID must be greater than zero")
    private Long shipmentId;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be greater than zero")
    private Long customerId;

    public static InvoiceDTO convertToDTO(Invoice entity) {
        if (entity == null) return null;
        return InvoiceDTO.builder().id(entity.getId()).amount(entity.getAmount())
                .status(entity.getStatus()).issuedDate(entity.getIssuedDate())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .build();
    }

    public static List<InvoiceDTO> convertToDTO(List<Invoice> entities) {
        return entities == null ? List.of()
                : entities.stream().map(InvoiceDTO::convertToDTO).toList();
    }

    public Invoice toEntity() {
        Invoice entity = new Invoice();
        entity.setAmount(amount);
        entity.setStatus(status);
        entity.setIssuedDate(issuedDate);
        if (shipmentId != null) {
            Shipment shipment = new Shipment();
            shipment.setId(shipmentId);
            entity.setShipment(shipment);
        }
        if (customerId != null) {
            Customer customer = new Customer();
            customer.setId(customerId);
            entity.setCustomer(customer);
        }
        return entity;
    }
}
