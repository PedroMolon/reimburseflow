package com.pedromolon.reimburseflow.infrastructure.web.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp,
        String error,
        Map<String, String> fields
) {
}
