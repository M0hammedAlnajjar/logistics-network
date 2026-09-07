package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findAllByIsActiveTrue();

    Optional<Route> findByIdAndIsActiveTrue(Long id);
}
