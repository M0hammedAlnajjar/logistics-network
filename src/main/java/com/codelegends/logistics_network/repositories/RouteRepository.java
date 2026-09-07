package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findAllByIsActiveTrue();

    Optional<Route> findByIdAndIsActiveTrue(Long id);

    @Query("""
            SELECT r FROM Route r
            WHERE r.isActive = true
              AND r.driver.id = :driverId
              AND r.routeDate = :routeDate
            """)
    List<Route> findActiveByDriverAndDate(
            @Param("driverId") Long driverId,
            @Param("routeDate") LocalDate routeDate);

    @Query("""
            SELECT COUNT(r) FROM Route r
            WHERE r.isActive = true
              AND r.vehicle.carrier.id = :carrierId
              AND LOWER(r.status) <> 'completed'
            """)
    long countActiveRoutesByCarrierId(
            @Param("carrierId") Long carrierId);
}
