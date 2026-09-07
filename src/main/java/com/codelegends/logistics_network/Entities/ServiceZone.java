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
public class ServiceZone extends BaseClass {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal baseRate;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceZone")
    private List<Address> addresses = new ArrayList<>();
}
