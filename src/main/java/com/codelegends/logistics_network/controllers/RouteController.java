package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.Entities.Route;
import com.codelegends.logistics_network.services.RouteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Route create(@Valid @RequestBody Route route) {
        return routeService.createRoute(route);
    }

    @GetMapping
    public List<Route> getAll() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public Route getById(@PathVariable Long id) {
        return routeService.getRouteById(id);
    }

    @PutMapping("/{id}")
    public Route update(
            @PathVariable Long id,
            @Valid @RequestBody Route route) {
        return routeService.updateRoute(id, route);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        routeService.softDeleteRoute(id);
    }
}
