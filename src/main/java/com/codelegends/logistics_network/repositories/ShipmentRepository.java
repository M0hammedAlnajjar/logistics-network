package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findAllByIsActiveTrue();

    Optional<Shipment> findByIdAndIsActiveTrue(Long id);

    @Query("""
            SELECT s FROM Shipment s
            WHERE s.isActive = true
              AND LOWER(s.status) = LOWER(:status)
            """)
    List<Shipment> findActiveByStatus(@Param("status") String status);

    @Query("""
            SELECT s FROM Shipment s
            WHERE s.isActive = true
              AND s.customer.id = :customerId
            ORDER BY s.shipmentDate DESC
            """)
    List<Shipment> findActiveCustomerHistory(@Param("customerId") Long customerId);

    @Query("""
            SELECT COUNT(s) FROM Shipment s
            WHERE s.isActive = true
              AND s.warehouse.id = :warehouseId
            """)
    long countActiveByWarehouseId(@Param("warehouseId") Long warehouseId);
}
