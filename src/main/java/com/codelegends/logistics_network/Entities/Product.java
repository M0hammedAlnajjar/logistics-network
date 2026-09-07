package com.codelegends.logistics_network.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Product extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;
}
