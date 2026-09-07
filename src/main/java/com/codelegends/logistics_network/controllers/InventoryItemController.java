package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.InventoryItem;
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
    public InventoryItem create(@Valid @RequestBody InventoryItem inventoryItem) {
        return inventoryItemService.createInventoryItem(inventoryItem);
    }

    @GetMapping
    public List<InventoryItem> getAll() {
        return inventoryItemService.getAllInventoryItems();
    }

    @GetMapping("/{id}")
    public InventoryItem getById(@PathVariable Long id) {
        return inventoryItemService.getInventoryItemById(id);
    }

    @PutMapping("/{id}")
    public InventoryItem update(
            @PathVariable Long id,
            @Valid @RequestBody InventoryItem inventoryItem) {
        return inventoryItemService.updateInventoryItem(id, inventoryItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inventoryItemService.softDeleteInventoryItem(id);
    }
}
