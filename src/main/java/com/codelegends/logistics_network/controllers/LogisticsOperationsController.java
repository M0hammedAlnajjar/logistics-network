package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.*;
import com.codelegends.logistics_network.dtos.operations.*;
import com.codelegends.logistics_network.dtos.stats.CarrierStatsDTO;
import com.codelegends.logistics_network.dtos.stats.CustomerStatsDTO;
import com.codelegends.logistics_network.dtos.stats.WarehouseStatsDTO;
import com.codelegends.logistics_network.services.LogisticsOperationsService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class LogisticsOperationsController {

    private final LogisticsOperationsService operationsService;

    public LogisticsOperationsController(
            LogisticsOperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @PostMapping("/shipments")
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentDTO createShipment(
            @Valid @RequestBody CreateShipmentRequest request) {
        return ShipmentDTO.convertToDTO(
                operationsService.createShipment(request));
    }

    @PutMapping("/shipments/{shipmentId}/carrier")
    public ShipmentDTO assignCarrier(
            @PathVariable Long shipmentId,
            @Valid @RequestBody AssignCarrierRequest request) {
        return ShipmentDTO.convertToDTO(
                operationsService.assignCarrier(shipmentId, request));
    }

    @PostMapping("/routes")
    @ResponseStatus(HttpStatus.CREATED)
    public RouteDTO buildRoute(
            @Valid @RequestBody BuildRouteRequest request) {
        return RouteDTO.convertToDTO(
                operationsService.buildRoute(request));
    }

    @PostMapping("/routes/{routeId}/stops")
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryStopDTO addDeliveryStop(
            @PathVariable Long routeId,
            @Valid @RequestBody AddDeliveryStopRequest request) {
        return DeliveryStopDTO.convertToDTO(
                operationsService.addDeliveryStop(routeId, request));
    }

    @PostMapping("/shipments/{shipmentId}/tracking-events")
    @ResponseStatus(HttpStatus.CREATED)
    public TrackingEventDTO appendTrackingEvent(
            @PathVariable Long shipmentId,
            @Valid @RequestBody AppendTrackingEventRequest request) {
        return TrackingEventDTO.convertToDTO(
                operationsService.appendTrackingEvent(
                        shipmentId, request));
    }

    @PutMapping("/delivery-stops/{stopId}/complete")
    public DeliveryStopDTO completeDeliveryStop(
            @PathVariable Long stopId) {
        return DeliveryStopDTO.convertToDTO(
                operationsService.completeDeliveryStop(stopId));
    }

    @PostMapping("/shipments/{shipmentId}/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceDTO generateInvoice(
            @PathVariable Long shipmentId,
            @Valid @RequestBody GenerateInvoiceRequest request) {
        return InvoiceDTO.convertToDTO(
                operationsService.generateInvoice(shipmentId, request));
    }

    @GetMapping("/customers/{customerId}/unpaid-invoices")
    public List<InvoiceDTO> getUnpaidInvoices(
            @PathVariable Long customerId) {
        return InvoiceDTO.convertToDTO(
                operationsService.getUnpaidInvoices(customerId));
    }

    @GetMapping("/queries/shipments/status")
    public List<ShipmentDTO> getShipmentsByStatus(
            @RequestParam String status) {
        return ShipmentDTO.convertToDTO(
                operationsService.getShipmentsByStatus(status));
    }

    @GetMapping("/queries/inventory/low-stock")
    public List<InventoryItemDTO> getLowStock(
            @RequestParam Integer threshold) {
        return InventoryItemDTO.convertToDTO(
                operationsService.getLowStock(threshold));
    }

    @GetMapping("/queries/routes")
    public List<RouteDTO> getDriverRoutes(
            @RequestParam Long driverId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return RouteDTO.convertToDTO(
                operationsService.getDriverRoutes(driverId, date));
    }

    @GetMapping("/queries/vehicles/available")
    public List<VehicleDTO> getAvailableVehicles() {
        return VehicleDTO.convertToDTO(
                operationsService.getAvailableVehicles());
    }

    @GetMapping("/queries/customers/{customerId}/shipments")
    public List<ShipmentDTO> getCustomerShipmentHistory(
            @PathVariable Long customerId) {
        return ShipmentDTO.convertToDTO(
                operationsService.getCustomerShipmentHistory(customerId));
    }

    @GetMapping("/stats/warehouses/{warehouseId}")
    public WarehouseStatsDTO getWarehouseStats(
            @PathVariable Long warehouseId) {
        return operationsService.getWarehouseStats(warehouseId);
    }

    @GetMapping("/stats/carriers/{carrierId}")
    public CarrierStatsDTO getCarrierStats(
            @PathVariable Long carrierId) {
        return operationsService.getCarrierStats(carrierId);
    }

    @GetMapping("/stats/customers/{customerId}")
    public CustomerStatsDTO getCustomerStats(
            @PathVariable Long customerId) {
        return operationsService.getCustomerStats(customerId);
    }
}
