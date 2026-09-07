package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByIsActiveTrue();

    Optional<Address> findByIdAndIsActiveTrue(Long id);
}
