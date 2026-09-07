package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.Carrier;
import com.codelegends.logistics_network.Entities.Customer;
import com.codelegends.logistics_network.Entities.Shipment;
import com.codelegends.logistics_network.Entities.Warehouse;
import com.codelegends.logistics_network.repositories.CarrierRepository;
import com.codelegends.logistics_network.repositories.CustomerRepository;
import com.codelegends.logistics_network.repositories.ShipmentRepository;
import com.codelegends.logistics_network.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;
    private final CarrierRepository carrierRepository;

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            WarehouseRepository warehouseRepository,
            CustomerRepository customerRepository,
            CarrierRepository carrierRepository) {
        this.shipmentRepository = shipmentRepository;
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.carrierRepository = carrierRepository;
    }

    public Shipment createShipment(Shipment shipment) {
        validateShipment(shipment);
        shipment.setId(null);
        shipment.setActive(true);
        shipment.setWarehouse(getWarehouse(shipment.getWarehouse()));
        shipment.setCustomer(getCustomer(shipment.getCustomer()));
        shipment.setCarrier(getOptionalCarrier(shipment.getCarrier()));
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAllByIsActiveTrue();
    }

    public Shipment getShipmentById(Long id) {
        validateId(id);
        return shipmentRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active shipment not found with ID: " + id));
    }

    public Shipment updateShipment(Long id, Shipment updatedShipment) {
        validateShipment(updatedShipment);
        Shipment shipment = getShipmentById(id);
        shipment.setShipmentDate(updatedShipment.getShipmentDate());
        shipment.setStatus(updatedShipment.getStatus());
        shipment.setTotalWeight(updatedShipment.getTotalWeight());
        shipment.setWarehouse(getWarehouse(updatedShipment.getWarehouse()));
        shipment.setCustomer(getCustomer(updatedShipment.getCustomer()));
        shipment.setCarrier(getOptionalCarrier(updatedShipment.getCarrier()));
        return shipmentRepository.save(shipment);
    }

    public void softDeleteShipment(Long id) {
        Shipment shipment = getShipmentById(id);
        shipment.setActive(false);
        shipmentRepository.save(shipment);
    }

    private void validateShipment(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment cannot be null");
        }
        if (shipment.getShipmentDate() == null) {
            throw new IllegalArgumentException("Shipment date is required");
        }
        if (shipment.getStatus() == null || shipment.getStatus().isBlank()) {
            throw new IllegalArgumentException("Shipment status is required");
        }
        if (shipment.getTotalWeight() == null
                || shipment.getTotalWeight().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total weight cannot be negative");
        }
    }

    private Warehouse getWarehouse(Warehouse warehouse) {
        if (warehouse == null || warehouse.getId() == null) {
            throw new IllegalArgumentException("Warehouse ID is required");
        }
        return warehouseRepository.findByIdAndIsActiveTrue(warehouse.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active warehouse not found"));
    }

    private Customer getCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        return customerRepository.findByIdAndIsActiveTrue(customer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active customer not found"));
    }

    private Carrier getOptionalCarrier(Carrier carrier) {
        if (carrier == null) {
            return null;
        }
        if (carrier.getId() == null) {
            throw new IllegalArgumentException("Carrier ID is required");
        }
        return carrierRepository.findByIdAndIsActiveTrue(carrier.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active carrier not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Shipment ID must be greater than zero");
        }
    }
}
