package com.codelegends.logistics_network.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Warehouse extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<InventoryItem> inventoryItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<Shipment> shipments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<Staff> staff = new ArrayList<>();
}
