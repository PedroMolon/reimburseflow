package com.pedromolon.reimburseflow.infrastructure.web.dto;

import java.time.LocalDateTime;

public record ReimbursementHistoryResponseDTO(
        Long id,
        Long reibursementeId,
        String changedByUserEmail,
        String previousStatus,
        String newStatus,
        String comment,
        LocalDateTime changedAt
) {
}
