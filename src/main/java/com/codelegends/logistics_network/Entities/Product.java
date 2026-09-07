package com.codelegends.logistics_network.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weightKg;

    @Column(nullable = false, length = 100)
    private String category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<InventoryItem> inventoryItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ShipmentItem> shipmentItems = new ArrayList<>();
}
