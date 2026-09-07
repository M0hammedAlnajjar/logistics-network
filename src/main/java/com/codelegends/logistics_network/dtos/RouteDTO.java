package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Driver;
import com.codelegends.logistics_network.Entities.Route;
import com.codelegends.logistics_network.Entities.Vehicle;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

    private Long id;

    @NotNull(message = "Route date is required")
    @FutureOrPresent(message = "Route date cannot be in the past")
    private LocalDate routeDate;

    @NotBlank(message = "Route origin is required")
    @Size(max = 150, message = "Route origin must not exceed 150 characters")
    private String origin;

    @NotBlank(message = "Route destination is required")
    @Size(max = 150, message = "Route destination must not exceed 150 characters")
    private String destination;

    @NotBlank(message = "Route status is required")
    @Size(max = 50, message = "Route status must not exceed 50 characters")
    private String status;

    @NotNull(message = "Vehicle ID is required")
    @Positive(message = "Vehicle ID must be greater than zero")
    private Long vehicleId;

    @NotNull(message = "Driver ID is required")
    @Positive(message = "Driver ID must be greater than zero")
    private Long driverId;

    public static RouteDTO convertToDTO(Route entity) {
        if (entity == null) return null;
        return RouteDTO.builder().id(entity.getId()).routeDate(entity.getRouteDate())
                .origin(entity.getOrigin()).destination(entity.getDestination())
                .status(entity.getStatus())
                .vehicleId(entity.getVehicle() == null ? null : entity.getVehicle().getId())
                .driverId(entity.getDriver() == null ? null : entity.getDriver().getId())
                .build();
    }

    public static List<RouteDTO> convertToDTO(List<Route> entities) {
        return entities == null ? List.of()
                : entities.stream().map(RouteDTO::convertToDTO).toList();
    }

    public Route toEntity() {
        Route entity = new Route();
        entity.setRouteDate(routeDate);
        entity.setOrigin(origin);
        entity.setDestination(destination);
        entity.setStatus(status);
        if (vehicleId != null) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(vehicleId);
            entity.setVehicle(vehicle);
        }
        if (driverId != null) {
            Driver driver = new Driver();
            driver.setId(driverId);
            entity.setDriver(driver);
        }
        return entity;
    }
}
