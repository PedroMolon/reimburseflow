package com.pedromolon.reimburseflow.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReimbursementResponseDTO(
        Long id,
        String description,
        BigDecimal amount,
        String category,
        String status,
        String userEmail,
        LocalDateTime createdAt
) {
}
