package com.pedromolon.reimburseflow.usecase;

import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.User;
import com.pedromolon.reimburseflow.domain.gateway.ReimbursementRepository;
import com.pedromolon.reimburseflow.domain.gateway.UserRepository;
import com.pedromolon.reimburseflow.usecase.dto.CreateReimbursement;

public class CreateReimbursementUseCase {

    private final ReimbursementRepository reimbursementRepository;
    private final UserRepository userRepository;

    public CreateReimbursementUseCase(ReimbursementRepository reimbursementRepository, UserRepository userRepository) {
        this.reimbursementRepository = reimbursementRepository;
        this.userRepository = userRepository;
    }

    public Reimbursement execute(CreateReimbursement create) {
        User user = userRepository.findById(create.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reimbursement reimbursement = new Reimbursement(
                user.getId(),
                create.description(),
                create.amount(),
                create.category()
        );

        return reimbursementRepository.save(reimbursement);
    }

}
