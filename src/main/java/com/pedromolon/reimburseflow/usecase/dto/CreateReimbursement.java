package com.pedromolon.reimburseflow.usecase.dto;

import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;

import java.math.BigDecimal;

public record CreateReimbursement(
        Long userId,
        String description,
        BigDecimal amount,
        ReimbursementCategory category
) {
}
