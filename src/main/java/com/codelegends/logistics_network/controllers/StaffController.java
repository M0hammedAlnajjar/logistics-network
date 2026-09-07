package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.Staff;
import com.codelegends.logistics_network.services.StaffService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Staff create(@Valid @RequestBody Staff staff) {
        return staffService.createStaff(staff);
    }

    @GetMapping
    public List<Staff> getAll() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public Staff getById(@PathVariable Long id) {
        return staffService.getStaffById(id);
    }

    @PutMapping("/{id}")
    public Staff update(
            @PathVariable Long id,
            @Valid @RequestBody Staff staff) {
        return staffService.updateStaff(id, staff);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        staffService.softDeleteStaff(id);
    }
}
