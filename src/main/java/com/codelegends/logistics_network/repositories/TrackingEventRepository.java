package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {

    List<TrackingEvent> findAllByIsActiveTrue();

    Optional<TrackingEvent> findByIdAndIsActiveTrue(Long id);
}
