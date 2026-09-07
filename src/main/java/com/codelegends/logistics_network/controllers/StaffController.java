package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.StaffDTO;
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
    public StaffDTO create(@Valid @RequestBody StaffDTO staffDTO) {
        return StaffDTO.convertToDTO(staffService.createStaff(staffDTO.toEntity()));
    }

    @GetMapping
    public List<StaffDTO> getAll() {
        return StaffDTO.convertToDTO(staffService.getAllStaff());
    }

    @GetMapping("/{id}")
    public StaffDTO getById(@PathVariable Long id) {
        return StaffDTO.convertToDTO(staffService.getStaffById(id));
    }

    @PutMapping("/{id}")
    public StaffDTO update(
            @PathVariable Long id,
            @Valid @RequestBody StaffDTO staffDTO) {
        return StaffDTO.convertToDTO(
                staffService.updateStaff(id, staffDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        staffService.softDeleteStaff(id);
    }
}
