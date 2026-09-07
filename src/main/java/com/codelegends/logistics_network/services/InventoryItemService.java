package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.InventoryItem;
import com.codelegends.logistics_network.Entities.Product;
import com.codelegends.logistics_network.Entities.Warehouse;
import com.codelegends.logistics_network.repositories.InventoryItemRepository;
import com.codelegends.logistics_network.repositories.ProductRepository;
import com.codelegends.logistics_network.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public InventoryItemService(
            InventoryItemRepository inventoryItemRepository,
            WarehouseRepository warehouseRepository,
            ProductRepository productRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    public InventoryItem createInventoryItem(InventoryItem item) {
        validateItem(item);
        item.setId(null);
        item.setActive(true);
        item.setWarehouse(getWarehouse(item.getWarehouse()));
        item.setProduct(getProduct(item.getProduct()));
        return inventoryItemRepository.save(item);
    }

    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAllByIsActiveTrue();
    }

    public InventoryItem getInventoryItemById(Long id) {
        validateId(id);
        return inventoryItemRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active inventory item not found with ID: " + id));
    }

    public InventoryItem updateInventoryItem(Long id, InventoryItem updatedItem) {
        validateItem(updatedItem);
        InventoryItem item = getInventoryItemById(id);
        item.setQuantity(updatedItem.getQuantity());
        item.setShelfLocation(updatedItem.getShelfLocation());
        item.setWarehouse(getWarehouse(updatedItem.getWarehouse()));
        item.setProduct(getProduct(updatedItem.getProduct()));
        return inventoryItemRepository.save(item);
    }

    public void softDeleteInventoryItem(Long id) {
        InventoryItem item = getInventoryItemById(id);
        item.setActive(false);
        inventoryItemRepository.save(item);
    }

    private void validateItem(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Inventory item cannot be null");
        }
        if (item.getQuantity() == null || item.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (item.getShelfLocation() == null || item.getShelfLocation().isBlank()) {
            throw new IllegalArgumentException("Shelf location is required");
        }
    }

    private Warehouse getWarehouse(Warehouse warehouse) {
        if (warehouse == null || warehouse.getId() == null) {
            throw new IllegalArgumentException("Warehouse ID is required");
        }
        return warehouseRepository.findByIdAndIsActiveTrue(warehouse.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active warehouse not found"));
    }

    private Product getProduct(Product product) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product ID is required");
        }
        return productRepository.findByIdAndIsActiveTrue(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active product not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Inventory item ID must be greater than zero");
        }
    }
}
