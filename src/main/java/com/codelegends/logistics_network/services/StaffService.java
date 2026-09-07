package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Staff;
import com.codelegends.logistics_network.Entities.Warehouse;
import com.codelegends.logistics_network.repositories.StaffRepository;
import com.codelegends.logistics_network.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final WarehouseRepository warehouseRepository;

    public StaffService(
            StaffRepository staffRepository,
            WarehouseRepository warehouseRepository) {
        this.staffRepository = staffRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Staff createStaff(Staff staff) {
        validateStaff(staff);
        staff.setId(null);
        staff.setActive(true);
        staff.setWarehouse(getWarehouse(staff.getWarehouse()));
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAllByIsActiveTrue();
    }

    public Staff getStaffById(Long id) {
        validateId(id);
        return staffRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active staff member not found with ID: " + id));
    }

    public Staff updateStaff(Long id, Staff updatedStaff) {
        validateStaff(updatedStaff);
        Staff staff = getStaffById(id);
        staff.setName(updatedStaff.getName());
        staff.setRole(updatedStaff.getRole());
        staff.setPhoneNumber(updatedStaff.getPhoneNumber());
        staff.setWarehouse(getWarehouse(updatedStaff.getWarehouse()));
        return staffRepository.save(staff);
    }

    public void softDeleteStaff(Long id) {
        Staff staff = getStaffById(id);
        staff.setActive(false);
        staffRepository.save(staff);
    }

    private void validateStaff(Staff staff) {
        if (staff == null) {
            throw new IllegalArgumentException("Staff member cannot be null");
        }
        if (staff.getName() == null || staff.getName().isBlank()) {
            throw new IllegalArgumentException("Staff name is required");
        }
        if (staff.getRole() == null || staff.getRole().isBlank()) {
            throw new IllegalArgumentException("Staff role is required");
        }
        if (staff.getPhoneNumber() == null || staff.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Staff phone number is required");
        }
    }

    private Warehouse getWarehouse(Warehouse warehouse) {
        if (warehouse == null || warehouse.getId() == null) {
            throw new IllegalArgumentException("Warehouse ID is required");
        }
        return warehouseRepository.findByIdAndIsActiveTrue(warehouse.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active warehouse not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Staff ID must be greater than zero");
        }
    }
}
