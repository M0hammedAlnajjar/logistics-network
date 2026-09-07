package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findAllByIsActiveTrue();

    Optional<InventoryItem> findByIdAndIsActiveTrue(Long id);

    @Query("""
            SELECT i FROM InventoryItem i
            WHERE i.isActive = true
              AND i.quantity < :threshold
            ORDER BY i.quantity ASC
            """)
    List<InventoryItem> findActiveBelowThreshold(
            @Param("threshold") Integer threshold);

    @Query("""
            SELECT i FROM InventoryItem i
            WHERE i.isActive = true
              AND i.warehouse.id = :warehouseId
              AND i.product.id = :productId
            """)
    Optional<InventoryItem> findActiveByWarehouseAndProduct(
            @Param("warehouseId") Long warehouseId,
            @Param("productId") Long productId);

    @Query("""
            SELECT COALESCE(SUM(i.quantity), 0) FROM InventoryItem i
            WHERE i.isActive = true
              AND i.warehouse.id = :warehouseId
            """)
    Long totalActiveUnitsByWarehouseId(
            @Param("warehouseId") Long warehouseId);
}
