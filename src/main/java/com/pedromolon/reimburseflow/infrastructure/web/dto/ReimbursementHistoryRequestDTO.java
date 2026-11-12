package com.pedromolon.reimburseflow.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReimbursementHistoryRequestDTO(
        @NotBlank(message = "New status is required")
        String newStatus,

        @Size(max = 500, message = "Comment must be less than 500 characters")
        String comment
) {
}
