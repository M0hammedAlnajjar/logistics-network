package com.codelegends.logistics_network.repositories;

import com.codelegends.logistics_network.Entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findAllByIsActiveTrue();

    Optional<Staff> findByIdAndIsActiveTrue(Long id);
}
