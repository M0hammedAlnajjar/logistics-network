package com.codelegends.logistics_network.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Route extends BaseClass {

    @Column(nullable = false)
    private LocalDate routeDate;

    @Column(nullable = false, length = 150)
    private String origin;

    @Column(nullable = false, length = 150)
    private String destination;

    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @JsonIgnore
    @OneToMany(mappedBy = "route")
    private List<DeliveryStop> deliveryStops = new ArrayList<>();
}
