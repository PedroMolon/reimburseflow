package com.pedromolon.reimburseflow.domain.gateway;

import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;

import java.util.List;
import java.util.Optional;

public interface ReimbursementRepository {
    Optional<Reimbursement> findById(Long id);
    List<Reimbursement> findByUserId(Long userId);
    List<Reimbursement> findByCategory(ReimbursementCategory category);
    List<Reimbursement> findByStatus(ReimbursementStatus status);
    Reimbursement save(Reimbursement reimbursement);
}
