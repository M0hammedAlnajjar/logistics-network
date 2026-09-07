package com.codelegends.logistics_network.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Warehouse extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String location;

    @Column(nullable = false)
    private Integer capacity;
}
