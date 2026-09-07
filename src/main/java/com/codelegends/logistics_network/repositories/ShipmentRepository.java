package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findAllByIsActiveTrue();

    Optional<Shipment> findByIdAndIsActiveTrue(Long id);
}
