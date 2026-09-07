package com.codelegends.logistics_network.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp) {
}
