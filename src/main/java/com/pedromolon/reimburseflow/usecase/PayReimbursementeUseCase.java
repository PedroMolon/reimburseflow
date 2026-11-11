package com.pedromolon.reimburseflow.usecase;

import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.gateway.ReimbursementRepository;

public class PayReimbursementeUseCase {

    private final ReimbursementRepository reimbursementRepository;

    public PayReimbursementeUseCase(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public Reimbursement execute(Long reimbursementId) {
        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new IllegalArgumentException("Reimbursement not found"));

        reimbursement.pay();

        return reimbursementRepository.save(reimbursement);
    }

}
