package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    List<Warehouse> findAllByIsActiveTrue();

    Optional<Warehouse> findByIdAndIsActiveTrue(Long id);
}
