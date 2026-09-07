package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.Invoice;
import com.codelegends.logistics_network.Entities.Shipment;
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
    private BigDecimal amount;
    private String status;
    private LocalDate issuedDate;
    private Long shipmentId;
    private Long customerId;

    public static InvoiceDTO convertToDTO(Invoice entity) {
        if (entity == null) {
            return null;
        }
        return InvoiceDTO.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .issuedDate(entity.getIssuedDate())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .build();
    }

    public static List<InvoiceDTO> convertToDTO(List<Invoice> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(InvoiceDTO::convertToDTO).toList();
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
