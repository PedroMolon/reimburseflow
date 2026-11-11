package com.pedromolon.reimburseflow.usecase;

import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.gateway.ReimbursementRepository;

public class ApproveReimbursementUseCase {

    private final ReimbursementRepository reimbursementRepository;

    public ApproveReimbursementUseCase(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public Reimbursement execute(Long reimbursementId) {
        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new IllegalArgumentException("Reimbursement not found"));

        reimbursement.approve();

        return reimbursementRepository.save(reimbursement);
    }

}
