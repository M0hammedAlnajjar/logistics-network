package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findAllByIsActiveTrue();

    Optional<Driver> findByIdAndIsActiveTrue(Long id);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumberAndIdNot(String licenseNumber, Long id);

    @Query("""
            SELECT COUNT(d) FROM Driver d
            WHERE d.isActive = true
              AND d.carrier.id = :carrierId
            """)
    long countActiveByCarrierId(@Param("carrierId") Long carrierId);
}
