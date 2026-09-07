package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByIsActiveTrue();

    Optional<Vehicle> findByIdAndIsActiveTrue(Long id);

    boolean existsByPlateNumber(String plateNumber);

    boolean existsByPlateNumberAndIdNot(String plateNumber, Long id);

    @Query("""
            SELECT v FROM Vehicle v
            WHERE v.isActive = true
              AND v.status = com.codelegends.logistics_network.enums.VehicleStatus.AVAILABLE
            """)
    List<Vehicle> findCurrentlyAvailable();

    @Query("""
            SELECT COUNT(v) FROM Vehicle v
            WHERE v.isActive = true
              AND v.carrier.id = :carrierId
            """)
    long countActiveByCarrierId(@Param("carrierId") Long carrierId);
}
