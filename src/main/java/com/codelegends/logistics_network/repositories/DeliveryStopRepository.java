package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.DeliveryStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryStopRepository extends JpaRepository<DeliveryStop, Long> {

    List<DeliveryStop> findAllByIsActiveTrue();

    Optional<DeliveryStop> findByIdAndIsActiveTrue(Long id);
}
