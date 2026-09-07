package com.codelegends.logistics_network.dtos.operations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddDeliveryStopRequest {

    private Long shipmentId;
    private Integer sequence;
    private String address;
    private LocalDateTime eta;
}
