package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {

    List<Carrier> findAllByIsActiveTrue();

    Optional<Carrier> findByIdAndIsActiveTrue(Long id);

    boolean existsByContactEmail(String contactEmail);

    boolean existsByContactEmailAndIdNot(String contactEmail, Long id);
}
