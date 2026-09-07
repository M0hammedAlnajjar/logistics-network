package com.codelegends.logistics_network.Entities;

import com.codelegends.logistics_network.enums.DeliveryStopStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "delivery_stop",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_route_stop_sequence",
                columnNames = {"route_id", "stop_sequence"}
        )
)
public class DeliveryStop extends BaseClass {

    @Column(name = "stop_sequence", nullable = false)
    private Integer sequence;

    @Column(nullable = false, length = 250)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DeliveryStopStatus status;

    @Column(nullable = false)
    private LocalDateTime eta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;
}
