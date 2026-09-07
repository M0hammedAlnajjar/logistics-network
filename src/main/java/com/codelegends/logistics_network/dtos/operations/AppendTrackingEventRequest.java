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
public class AppendTrackingEventRequest {

    private LocalDateTime eventTime;
    private String location;
    private String status;
    private String note;
}
