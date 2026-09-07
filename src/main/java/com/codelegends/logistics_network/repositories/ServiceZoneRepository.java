package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.ServiceZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceZoneRepository extends JpaRepository<ServiceZone, Long> {

    List<ServiceZone> findAllByIsActiveTrue();

    Optional<ServiceZone> findByIdAndIsActiveTrue(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
