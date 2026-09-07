package com.codelegends.logistics_network.Entities;

import com.codelegends.logistics_network.enums.ShipmentStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Shipment extends BaseClass {

    @Column(nullable = false)
    private LocalDate shipmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ShipmentStatus status;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalWeight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_id")
    private Carrier carrier;

    @JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<ShipmentItem> shipmentItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<TrackingEvent> trackingEvents = new ArrayList<>();
}
