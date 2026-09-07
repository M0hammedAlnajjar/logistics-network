package com.codelegends.logistics_network.dtos.operations;

import com.codelegends.logistics_network.enums.TrackingStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Tracking event time is required")
    @PastOrPresent(message = "Tracking event time cannot be in the future")
    private LocalDateTime eventTime;

    @NotBlank(message = "Tracking event location is required")
    @Size(max = 150, message = "Tracking event location must not exceed 150 characters")
    private String location;

    @NotNull(message = "Tracking event status is required")
    private TrackingStatus status;

    @Size(max = 500, message = "Tracking event note must not exceed 500 characters")
    private String note;
}
