package com.pedromolon.reimburseflow.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ReimbursementRequestDTO(
        @NotBlank(message = "Description is required")
        @Size(max = 255, message = "Description must be less than 255 characters")
        String description,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount,

        com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory category
) {
}
