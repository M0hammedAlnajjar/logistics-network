package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByIsActiveTrue();

    Optional<Vehicle> findByIdAndIsActiveTrue(Long id);

    boolean existsByPlateNumber(String plateNumber);

    boolean existsByPlateNumberAndIdNot(String plateNumber, Long id);
}
