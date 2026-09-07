package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByIsActiveTrue();

    Optional<Invoice> findByIdAndIsActiveTrue(Long id);

    @Query("""
            SELECT i FROM Invoice i
            WHERE i.isActive = true
              AND i.customer.id = :customerId
              AND LOWER(i.status) = 'unpaid'
            ORDER BY i.issuedDate DESC
            """)
    List<Invoice> findUnpaidByCustomerId(
            @Param("customerId") Long customerId);

    @Query("""
            SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END
            FROM Invoice i
            WHERE i.isActive = true
              AND i.shipment.id = :shipmentId
            """)
    boolean existsActiveByShipmentId(
            @Param("shipmentId") Long shipmentId);

    @Query("""
            SELECT COALESCE(SUM(i.amount), 0) FROM Invoice i
            WHERE i.isActive = true
              AND i.customer.id = :customerId
            """)
    BigDecimal totalInvoicedByCustomerId(
            @Param("customerId") Long customerId);
}
