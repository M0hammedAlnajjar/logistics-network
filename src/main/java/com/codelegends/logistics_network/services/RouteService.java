package com.codelegends.logistics_network.services;

import com.codelegends.logistics_network.Entities.Driver;
import com.codelegends.logistics_network.Entities.Route;
import com.codelegends.logistics_network.Entities.Vehicle;
import com.codelegends.logistics_network.repositories.DriverRepository;
import com.codelegends.logistics_network.repositories.RouteRepository;
import com.codelegends.logistics_network.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public RouteService(
            RouteRepository routeRepository,
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository) {
        this.routeRepository = routeRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }

    public Route createRoute(Route route) {
        validateRoute(route);
        route.setId(null);
        route.setActive(true);
        route.setVehicle(getVehicle(route.getVehicle()));
        route.setDriver(getDriver(route.getDriver()));
        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAllByIsActiveTrue();
    }

    public Route getRouteById(Long id) {
        validateId(id);
        return routeRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active route not found with ID: " + id));
    }

    public Route updateRoute(Long id, Route updatedRoute) {
        validateRoute(updatedRoute);
        Route route = getRouteById(id);
        route.setRouteDate(updatedRoute.getRouteDate());
        route.setOrigin(updatedRoute.getOrigin());
        route.setDestination(updatedRoute.getDestination());
        route.setStatus(updatedRoute.getStatus());
        route.setVehicle(getVehicle(updatedRoute.getVehicle()));
        route.setDriver(getDriver(updatedRoute.getDriver()));
        return routeRepository.save(route);
    }

    public void softDeleteRoute(Long id) {
        Route route = getRouteById(id);
        route.setActive(false);
        routeRepository.save(route);
    }

    private void validateRoute(Route route) {
        if (route == null) {
            throw new IllegalArgumentException("Route cannot be null");
        }
        if (route.getRouteDate() == null) {
            throw new IllegalArgumentException("Route date is required");
        }
        if (route.getOrigin() == null || route.getOrigin().isBlank()) {
            throw new IllegalArgumentException("Route origin is required");
        }
        if (route.getDestination() == null || route.getDestination().isBlank()) {
            throw new IllegalArgumentException("Route destination is required");
        }
        if (route.getStatus() == null || route.getStatus().isBlank()) {
            throw new IllegalArgumentException("Route status is required");
        }
    }

    private Vehicle getVehicle(Vehicle vehicle) {
        if (vehicle == null || vehicle.getId() == null) {
            throw new IllegalArgumentException("Vehicle ID is required");
        }
        return vehicleRepository.findByIdAndIsActiveTrue(vehicle.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active vehicle not found"));
    }

    private Driver getDriver(Driver driver) {
        if (driver == null || driver.getId() == null) {
            throw new IllegalArgumentException("Driver ID is required");
        }
        return driverRepository.findByIdAndIsActiveTrue(driver.getId())
                .orElseThrow(() -> new IllegalArgumentException("Active driver not found"));
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Route ID must be greater than zero");
        }
    }
}
