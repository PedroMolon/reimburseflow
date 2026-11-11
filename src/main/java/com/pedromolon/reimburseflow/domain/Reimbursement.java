package com.pedromolon.reimburseflow.domain;

import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;

import java.time.LocalDateTime;

public class Reimbursement {
    private final Long id;
    private final Long userId;
    private final String description;
    private final Money amount;
    private final ReimbursementCategory category;
    private final ReimbursementStatus status;
    private final LocalDateTime createdAt;

    public Reimbursement(Long id, Long userId, String description, Money amount, ReimbursementCategory category, ReimbursementStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public Money getAmount() {
        return amount;
    }

    public ReimbursementCategory getCategory() {
        return category;
    }

    public ReimbursementStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
