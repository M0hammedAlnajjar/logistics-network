package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.*;
import com.codelegends.logistics_network.dtos.operations.*;
import com.codeleg Echtends.logistics_network.dtos.stats.CarrierStatsDTO;
import com.codelegends.logistics_network.dtos.stats.CustomerStatsDTO;
import com.codelegends.logistics_network.dtos.stats.WarehouseStatsDTO;
import com.codelegends.logistics_network.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LogisticsOperationsService {

    private static final Set<String> TRACKING_STATUSES =
            Set.of("PICKED_UP", "IN_TRANSIT", "DELIVERED");

    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShipmentItemRepository shipmentItemRepository;
    private final CarrierRepository carrierRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final RouteRepository routeRepository;
    private final DeliveryStopRepository deliveryStopRepository;
    private final TrackingEventRepository trackingEventRepository;
    private final InvoiceRepository invoiceRepository;

    public LogisticsOperationsService(
            WarehouseRepository warehouseRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            InventoryItemRepository inventoryItemRepository,
            ShipmentRepository shipmentRepository,
            ShipmentItemRepository shipmentItemRepository,
            CarrierRepository carrierRepository,
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository,
            RouteRepository routeRepository,
            DeliveryStopRepository deliveryStopRepository,
            TrackingEventRepository trackingEventRepository,
            InvoiceRepository invoiceRepository) {
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.shipmentRepository = shipmentRepository;
        this.shipmentItemRepository = shipmentItemRepository;
        this.carrierRepository = carrierRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.routeRepository = routeRepository;
        this.deliveryStopRepository = deliveryStopRepository;
        this.trackingEventRepository = trackingEventRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public Shipment createShipment(CreateShipmentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Shipment request cannot be null");
        }
        Warehouse warehouse = getWarehouse(request.getWarehouseId());
        Customer customer = getCustomer(request.getCustomerId());
        if (request.getShipmentDate() == null) {
            throw new IllegalArgumentException("Shipment date is required");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Shipment must contain at least one item");
        }

        Map<Long, Integer> requestedQuantities = new LinkedHashMap<>();
        for (ShipmentLineRequest line : request.getItems()) {
            if (line == null || line.getProductId() == null) {
                throw new IllegalArgumentException("Product ID is required");
            }
            if (line.getQuantity() == null || line.getQuantity() <= 0) {
                throw new IllegalArgumentException("Item quantity must be greater than zero");
            }
            requestedQuantities.merge(
                    line.getProductId(), line.getQuantity(), Integer::sum);
        }

        List<PreparedLine> preparedLines = new ArrayList<>();
        BigDecimal totalWeight = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : requestedQuantities.entrySet()) {
            Product product = getProduct(entry.getKey());
            InventoryItem inventoryItem = inventoryItemRepository
                    .findActiveByWarehouseAndProduct(
                            warehouse.getId(), product.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Product is not stocked in the selected warehouse"));

            if (inventoryItem.getQuantity() < entry.getValue()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: " + product.getName());
            }

            preparedLines.add(new PreparedLine(
                    product, inventoryItem, entry.getValue()));
            totalWeight = totalWeight.add(
                    product.getWeightKg().multiply(
                            BigDecimal.valueOf(entry.getValue())));
        }

        Shipment shipment = new Shipment();
        shipment.setShipmentDate(request.getShipmentDate());
        shipment.setStatus("PENDING");
        shipment.setTotalWeight(totalWeight);
        shipment.setWarehouse(warehouse);
        shipment.setCustomer(customer);
        shipment.setActive(true);
        shipment = shipmentRepository.save(shipment);

        for (PreparedLine line : preparedLines) {
            InventoryItem inventoryItem = line.inventoryItem();
            inventoryItem.setQuantity(
                    inventoryItem.getQuantity() - line.quantity());
            inventoryItemRepository.save(inventoryItem);

            ShipmentItem shipmentItem = new ShipmentItem();
            shipmentItem.setShipment(shipment);
            shipmentItem.setProduct(line.product());
            shipmentItem.setQuantity(line.quantity());
            shipmentItem.setActive(true);
            shipmentItemRepository.save(shipmentItem);
        }

        return shipment;
    }

    @Transactional
    public Shipment assignCarrier(
            Long shipmentId,
            AssignCarrierRequest request) {
        Shipment shipment = getShipment(shipmentId);
        if (request == null || request.getCarrierId() == null) {
            throw new IllegalArgumentException("Carrier ID is required");
        }
        Carrier carrier = getCarrier(request.getCarrierId());
        shipment.setCarrier(carrier);
        return shipmentRepository.save(shipment);
    }

    @Transactional
    public Route buildRoute(BuildRouteRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Route request cannot be null");
        }
        Shipment shipment = getShipment(request.getShipmentId());
        if (shipment.getCarrier() == null) {
            throw new IllegalArgumentException(
                    "Shipment must be assigned to a carrier first");
        }

        Vehicle vehicle = getVehicle(request.getVehicleId());
        Driver driver = getDriver(request.getDriverId());

        if (!"AVAILABLE".equalsIgnoreCase(vehicle.getStatus())) {
            throw new IllegalArgumentException("Vehicle is not available");
        }
        if (!"AVAILABLE".equalsIgnoreCase(driver.getStatus())) {
            throw new IllegalArgumentException("Driver is not available");
        }
        if (!Objects.equals(
                vehicle.getCarrier().getId(),
                shipment.getCarrier().getId())) {
            throw new IllegalArgumentException(
                    "Vehicle does not belong to the shipment carrier");
        }
        if (!Objects.equals(
                driver.getCarrier().getId(),
                shipment.getCarrier().getId())) {
            throw new IllegalArgumentException(
                    "Driver does not belong to the shipment carrier");
        }
        if (vehicle.getCapacityKg().compareTo(shipment.getTotalWeight()) < 0) {
            throw new IllegalArgumentException(
                    "Shipment weight exceeds vehicle capacity");
        }
        if (request.getRouteDate() == null) {
            throw new IllegalArgumentException("Route date is required");
        }
        if (isBlank(request.getOrigin()) || isBlank(request.getDestination())) {
            throw new IllegalArgumentException(
                    "Route origin and destination are required");
        }

        Route route = new Route();
        route.setRouteDate(request.getRouteDate());
        route.setOrigin(request.getOrigin());
        route.setDestination(request.getDestination());
        route.setStatus("PLANNED");
        route.setVehicle(vehicle);
        route.setDriver(driver);
        route.setActive(true);
        route = routeRepository.save(route);

        vehicle.setStatus("BUSY");
        driver.setStatus("BUSY");
        vehicleRepository.save(vehicle);
        driverRepository.save(driver);

        return route;
    }

    @Transactional
    public DeliveryStop addDeliveryStop(
            Long routeId,
            AddDeliveryStopRequest request) {
        Route route = getRoute(routeId);
        if (request == null) {
            throw new IllegalArgumentException(
                    "Delivery stop request cannot be null");
        }
        Shipment shipment = getShipment(request.getShipmentId());
        if (request.getSequence() == null || request.getSequence() <= 0) {
            throw new IllegalArgumentException(
                    "Delivery stop sequence must be greater than zero");
        }
        if (deliveryStopRepository.existsActiveByRouteAndSequence(
                routeId, request.getSequence())) {
            throw new IllegalArgumentException(
                    "Duplicate delivery stop sequence on this route");
        }
        if (isBlank(request.getAddress())) {
            throw new IllegalArgumentException(
                    "Delivery stop address is required");
        }
        if (request.getEta() == null) {
            throw new IllegalArgumentException("Delivery stop ETA is required");
        }

        DeliveryStop stop = new DeliveryStop();
        stop.setSequence(request.getSequence());
        stop.setAddress(request.getAddress());
        stop.setStatus("PENDING");
        stop.setEta(request.getEta());
        stop.setRoute(route);
        stop.setShipment(shipment);
        stop.setActive(true);
        return deliveryStopRepository.save(stop);
    }

    @Transactional
    public TrackingEvent appendTrackingEvent(
            Long shipmentId,
            AppendTrackingEventRequest request) {
        Shipment shipment = getShipment(shipmentId);
        if (request == null) {
            throw new IllegalArgumentException(
                    "Tracking event request cannot be null");
        }
        if (request.getEventTime() == null) {
            throw new IllegalArgumentException(
                    "Tracking event time is required");
        }
        if (isBlank(request.getLocation())) {
            throw new IllegalArgumentException(
                    "Tracking event location is required");
        }

        String status = normalizeStatus(request.getStatus());
        if (!TRACKING_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                    "Tracking status must be PICKED_UP, IN_TRANSIT, or DELIVERED");
        }

        TrackingEvent event = new TrackingEvent();
        event.setEventTime(request.getEventTime());
        event.setLocation(request.getLocation());
        event.setStatus(status);
        event.setNote(request.getNote());
        event.setShipment(shipment);
        event.setActive(true);
        event = trackingEventRepository.save(event);

        shipment.setStatus(status);
        shipmentRepository.save(shipment);
        return event;
    }

    @Transactional
    public DeliveryStop completeDeliveryStop(Long stopId) {
        DeliveryStop stop = deliveryStopRepository
                .findByIdAndIsActiveTrue(stopId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active delivery stop not found"));
        stop.setStatus("COMPLETED");
        stop = deliveryStopRepository.save(stop);

        List<DeliveryStop> routeStops =
                deliveryStopRepository.findActiveByRouteId(
                        stop.getRoute().getId());
        boolean allCompleted = !routeStops.isEmpty()
                && routeStops.stream().allMatch(item ->
                "COMPLETED".equalsIgnoreCase(item.getStatus()));

        if (allCompleted) {
            Route route = stop.getRoute();
            route.setStatus("COMPLETED");
            routeRepository.save(route);
        }
        return stop;
    }

    @Transactional
    public Invoice generateInvoice(
            Long shipmentId,
            GenerateInvoiceRequest request) {
        Shipment shipment = getShipment(shipmentId);
        if (!"DELIVERED".equalsIgnoreCase(shipment.getStatus())) {
            throw new IllegalArgumentException(
                    "Invoice can only be generated for a delivered shipment");
        }
        if (invoiceRepository.existsActiveByShipmentId(shipmentId)) {
            throw new IllegalArgumentException(
                    "An active invoice already exists for this shipment");
        }
        if (request == null || request.getAmount() == null
                || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Invoice amount must be greater than zero");
        }
        if (request.getIssuedDate() == null) {
            throw new IllegalArgumentException(
                    "Invoice issued date is required");
        }

        Invoice invoice = new Invoice();
        invoice.setAmount(request.getAmount());
        invoice.setStatus("UNPAID");
        invoice.setIssuedDate(request.getIssuedDate());
        invoice.setShipment(shipment);
        invoice.setCustomer(shipment.getCustomer());
        invoice.setActive(true);
        return invoiceRepository.save(invoice);
    }

    @Transactional(readOnly = true)
    public List<Invoice> getUnpaidInvoices(Long customerId) {
        getCustomer(customerId);
        return invoiceRepository.findUnpaidByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<Shipment> getShipmentsByStatus(String status) {
        if (isBlank(status)) {
            throw new IllegalArgumentException("Shipment status is required");
        }
        return shipmentRepository.findActiveByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getLowStock(Integer threshold) {
        if (threshold == null || threshold <= 0) {
            throw new IllegalArgumentException(
                    "Reorder threshold must be greater than zero");
        }
        return inventoryItemRepository.findActiveBelowThreshold(threshold);
    }

    @Transactional(readOnly = true)
    public List<Route> getDriverRoutes(
            Long driverId,
            java.time.LocalDate routeDate) {
        getDriver(driverId);
        if (routeDate == null) {
            throw new IllegalArgumentException("Route date is required");
        }
        return routeRepository.findActiveByDriverAndDate(
                driverId, routeDate);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findCurrentlyAvailable();
    }

    @Transactional(readOnly = true)
    public List<Shipment> getCustomerShipmentHistory(Long customerId) {
        getCustomer(customerId);
        return shipmentRepository.findActiveCustomerHistory(customerId);
    }

    @Transactional(readOnly = true)
    public WarehouseStatsDTO getWarehouseStats(Long warehouseId) {
        getWarehouse(warehouseId);
        Long totalUnits =
                inventoryItemRepository.totalActiveUnitsByWarehouseId(
                        warehouseId);
        return WarehouseStatsDTO.builder()
                .warehouseId(warehouseId)
                .activeShipments(
                        shipmentRepository.countActiveByWarehouseId(
                                warehouseId))
                .totalInventoryUnits(totalUnits == null ? 0 : totalUnits)
                .build();
    }

    @Transactional(readOnly = true)
    public CarrierStatsDTO getCarrierStats(Long carrierId) {
        getCarrier(carrierId);
        return CarrierStatsDTO.builder()
                .carrierId(carrierId)
                .vehicles(vehicleRepository.countActiveByCarrierId(carrierId))
                .drivers(driverRepository.countActiveByCarrierId(carrierId))
                .activeRoutes(
                        routeRepository.countActiveRoutesByCarrierId(
                                carrierId))
                .build();
    }

    @Transactional(readOnly = true)
    public CustomerStatsDTO getCustomerStats(Long customerId) {
        getCustomer(customerId);
        BigDecimal total =
                invoiceRepository.totalInvoicedByCustomerId(customerId);
        return CustomerStatsDTO.builder()
                .customerId(customerId)
                .totalInvoicedAmount(
                        total == null ? BigDecimal.ZERO : total)
                .build();
    }

    private Warehouse getWarehouse(Long id) {
        validateId(id, "Warehouse");
        return warehouseRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active warehouse not found"));
    }

    private Customer getCustomer(Long id) {
        validateId(id, "Customer");
        return customerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active customer not found"));
    }

    private Product getProduct(Long id) {
        validateId(id, "Product");
        return productRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active product not found"));
    }

    private Shipment getShipment(Long id) {
        validateId(id, "Shipment");
        return shipmentRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active shipment not found"));
    }

    private Carrier getCarrier(Long id) {
        validateId(id, "Carrier");
        return carrierRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active carrier not found"));
    }

    private Vehicle getVehicle(Long id) {
        validateId(id, "Vehicle");
        return vehicleRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active vehicle not found"));
    }

    private Driver getDriver(Long id) {
        validateId(id, "Driver");
        return driverRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active driver not found"));
    }

    private Route getRoute(Long id) {
        validateId(id, "Route");
        return routeRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active route not found"));
    }

    private void validateId(Long id, String fieldName) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    fieldName + " ID must be greater than zero");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String normalizeStatus(String status) {
        if (isBlank(status)) {
            throw new IllegalArgumentException(
                    "Tracking status is required");
        }
        return status.trim()
                .toUpperCase(Locale.ROOT)
                .replace(' ', '_');
    }

    private record PreparedLine(
            Product product,
            InventoryItem inventoryItem,
            int quantity) {
    }
}
