package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.exceptions.ResourceNotFoundException;

import com.codelegends.logistics_network.Entities.Product;
import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.Entities.ShipmentItem;
import com.codelegends.logistics_network.repositories.ProductRepository;
import com.codelegends.logistics_network.repositories.ShipmentItemRepository;
import com.codelegends.logistics_network.repositories.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentItemService {

    private final ShipmentItemRepository shipmentItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;

    public ShipmentItemService(
            ShipmentItemRepository shipmentItemRepository,
            ShipmentRepository shipmentRepository,
            ProductRepository productRepository) {
        this.shipmentItemRepository = shipmentItemRepository;
        this.shipmentRepository = shipmentRepository;
        this.productRepository = productRepository;
    }

    public ShipmentItem createShipmentItem(ShipmentItem item) {
        validateItem(item);
        item.setId(null);
        item.setActive(true);
        item.setShipment(getShipment(item.getShipment()));
        item.setProduct(getProduct(item.getProduct()));
        return shipmentItemRepository.save(item);
    }

    public List<ShipmentItem> getAllShipmentItems() {
        return shipmentItemRepository.findAllByIsActiveTrue();
    }

    public ShipmentItem getShipmentItemById(Long id) {
        validateId(id);
        return shipmentItemRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active shipment item not found with ID: " + id));
    }

    public ShipmentItem updateShipmentItem(Long id, ShipmentItem updatedItem) {
        validateItem(updatedItem);
        ShipmentItem item = getShipmentItemById(id);
        item.setQuantity(updatedItem.getQuantity());
        item.setShipment(getShipment(updatedItem.getShipment()));
        item.setProduct(getProduct(updatedItem.getProduct()));
        return shipmentItemRepository.save(item);
    }

    public void softDeleteShipmentItem(Long id) {
        ShipmentItem item = getShipmentItemById(id);
        item.setActive(false);
        shipmentItemRepository.save(item);
    }

    private void validateItem(ShipmentItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Shipment item cannot be null");
        }
        if (item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }

    private Shipment getShipment(Shipment shipment) {
        if (shipment == null || shipment.getId() == null) {
            throw new IllegalArgumentException("Shipment ID is required");
        }
        return shipmentRepository.findByIdAndIsActiveTrue(shipment.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active shipment not found"));
    }

    private Product getProduct(Product product) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product ID is required");
        }
        return productRepository.findByIdAndIsActiveTrue(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active product not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Shipment item ID must be greater than zero");
        }
    }
}
