package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findAllByIsActiveTrue();

    Optional<InventoryItem> findByIdAndIsActiveTrue(Long id);
}
