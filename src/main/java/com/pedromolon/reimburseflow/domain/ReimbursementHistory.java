package com.pedromolon.reimburseflow.domain;

import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;

import java.time.LocalDateTime;

public class ReimbursementHistory {
    private final Long id;
    private final Long reimbursementId;
    private final Long changedByUserId;
    private final ReimbursementStatus previousStatus;
    private final ReimbursementStatus nextStatus;
    private final String comment;
    private final LocalDateTime changedAt;

    public ReimbursementHistory(Long id, Long reimbursementId, Long changedByUserId, ReimbursementStatus previousStatus, ReimbursementStatus nextStatus, String comment, LocalDateTime changedAt) {
        this.id = id;
        this.reimbursementId = reimbursementId;
        this.changedByUserId = changedByUserId;
        this.previousStatus = previousStatus;
        this.nextStatus = nextStatus;
        this.comment = comment;
        this.changedAt = changedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getReimbursementId() {
        return reimbursementId;
    }

    public Long getChangedByUserId() {
        return changedByUserId;
    }

    public ReimbursementStatus getPreviousStatus() {
        return previousStatus;
    }

    public ReimbursementStatus getNextStatus() {
        return nextStatus;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}
