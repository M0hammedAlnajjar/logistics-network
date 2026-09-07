package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.DeliveryStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryStopRepository extends JpaRepository<DeliveryStop, Long> {

    List<DeliveryStop> findAllByIsActiveTrue();

    Optional<DeliveryStop> findByIdAndIsActiveTrue(Long id);

    @Query("""
            SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END
            FROM DeliveryStop d
            WHERE d.isActive = true
              AND d.route.id = :routeId
              AND d.sequence = :sequence
            """)
    boolean existsActiveByRouteAndSequence(
            @Param("routeId") Long routeId,
            @Param("sequence") Integer sequence);

    @Query("""
            SELECT d FROM DeliveryStop d
            WHERE d.isActive = true
              AND d.route.id = :routeId
            ORDER BY d.sequence
            """)
    List<DeliveryStop> findActiveByRouteId(
            @Param("routeId") Long routeId);
}
