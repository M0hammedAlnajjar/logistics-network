package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.InventoryItemDTO;
import com.codelegends.logistics_network.services.InventoryItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-items")
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryItemDTO create(@Valid @RequestBody InventoryItemDTO inventoryItemDTO) {
        return InventoryItemDTO.convertToDTO(inventoryItemService.createInventoryItem(inventoryItemDTO.toEntity()));
    }

    @GetMapping
    public List<InventoryItemDTO> getAll() {
        return InventoryItemDTO.convertToDTO(inventoryItemService.getAllInventoryItems());
    }

    @GetMapping("/{id}")
    public InventoryItemDTO getById(@PathVariable Long id) {
        return InventoryItemDTO.convertToDTO(inventoryItemService.getInventoryItemById(id));
    }

    @PutMapping("/{id}")
    public InventoryItemDTO update(
            @PathVariable Long id,
            @Valid @RequestBody InventoryItemDTO inventoryItemDTO) {
        return InventoryItemDTO.convertToDTO(
                inventoryItemService.updateInventoryItem(id, inventoryItemDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inventoryItemService.softDeleteInventoryItem(id);
    }
}
