package com.codelegends.logistics_network.dtos;

import com.codelegends.logistics_network.Entities.Driver;
import com.codelegends.logistics_network.Entities.Route;
import com.codelegends.logistics_network.Entities.Vehicle;
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
    private LocalDate routeDate;
    private String origin;
    private String destination;
    private String status;
    private Long vehicleId;
    private Long driverId;

    public static RouteDTO convertToDTO(Route entity) {
        if (entity == null) {
            return null;
        }
        return RouteDTO.builder()
                .id(entity.getId())
                .routeDate(entity.getRouteDate())
                .origin(entity.getOrigin())
                .destination(entity.getDestination())
                .status(entity.getStatus())
                .vehicleId(entity.getVehicle() == null ? null : entity.getVehicle().getId())
                .driverId(entity.getDriver() == null ? null : entity.getDriver().getId())
                .build();
    }

    public static List<RouteDTO> convertToDTO(List<Route> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(RouteDTO::convertToDTO).toList();
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
