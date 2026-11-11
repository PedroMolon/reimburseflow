package com.pedromolon.reimburseflow.domain;

import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Reimbursement {
    private final Long id;
    private final Long userId;
    private final String description;
    private final Money amount;
    private final ReimbursementCategory category;
    private ReimbursementStatus status;
    private final LocalDateTime createdAt;

    public Reimbursement(Long userId, String description, BigDecimal amount, ReimbursementCategory category) {
        this.id = null;
        this.userId = userId;
        this.description = description;
        this.amount = new Money(amount);
        this.category = category;
        this.status = ReimbursementStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
    }

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

    public void submit() {
        if (this.status != ReimbursementStatus.DRAFT) {
            throw new IllegalStateException("Reimbursement can just be submitted in DRAFT status");
        }
        this.status = ReimbursementStatus.SUBMITTED;
    }

    public void approve() {
        if (this.status != ReimbursementStatus.SUBMITTED) {
            throw new IllegalStateException("Reimbursement can just be approved in SUBMITTED status");
        }
        this.status = ReimbursementStatus.APPROVED;
    }

    public void reject(String comment) {
        if (this.status != ReimbursementStatus.SUBMITTED) {
            throw new IllegalStateException("Reimbursement can just be rejected in SUBMITTED status");
        }
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment cannot be null or blank");
        }
        this.status = ReimbursementStatus.REJECTED;
    }

    public void pay() {
        if (this.status != ReimbursementStatus.APPROVED) {
            throw new IllegalStateException("Reimbursement can just be paid in APPROVED status");
        }
        this.status = ReimbursementStatus.PAID;
    }

}
