package com.pedromolon.reimburseflow.domain.gateway;

import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.User;

import java.util.Optional;

public interface ReimbursementRepository {
    Optional<Reimbursement> findById(Long id);
    Reimbursement save(Reimbursement reimbursement);
}
