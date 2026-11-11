package com.pedromolon.reimburseflow.usecase;

import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.gateway.ReimbursementRepository;
import com.pedromolon.reimburseflow.usecase.dto.RejectReimbursement;

public class RejectReimbursementUseCase {

    private final ReimbursementRepository reimbursementRepository;

    public RejectReimbursementUseCase(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public Reimbursement execute(RejectReimbursement rejectReimbursement) {
        Reimbursement reimbursement = reimbursementRepository.findById(rejectReimbursement.id())
                .orElseThrow(() -> new IllegalArgumentException("Reimbursement not found"));

        reimbursement.reject(rejectReimbursement.reason());

        return reimbursementRepository.save(reimbursement);
    }

}
