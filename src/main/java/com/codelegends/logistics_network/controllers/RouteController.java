package com.codelegends.logistics_network.controllers;

import com.codelegends.logistics_network.dtos.RouteDTO;
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
    public RouteDTO create(@Valid @RequestBody RouteDTO routeDTO) {
        return RouteDTO.convertToDTO(routeService.createRoute(routeDTO.toEntity()));
    }

    @GetMapping
    public List<RouteDTO> getAll() {
        return RouteDTO.convertToDTO(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public RouteDTO getById(@PathVariable Long id) {
        return RouteDTO.convertToDTO(routeService.getRouteById(id));
    }

    @PutMapping("/{id}")
    public RouteDTO update(
            @PathVariable Long id,
            @Valid @RequestBody RouteDTO routeDTO) {
        return RouteDTO.convertToDTO(
                routeService.updateRoute(id, routeDTO.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        routeService.softDeleteRoute(id);
    }
}
