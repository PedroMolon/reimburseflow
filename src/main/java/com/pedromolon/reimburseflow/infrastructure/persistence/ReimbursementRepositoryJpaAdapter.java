package com.pedromolon.reimburseflow.infrastructure.persistence;

import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.gateway.ReimbursementRepository;
import com.pedromolon.reimburseflow.infrastructure.entity.UserEntity;
import com.pedromolon.reimburseflow.infrastructure.mapper.EntityMapper;
import com.pedromolon.reimburseflow.infrastructure.repository.SpringReimbursementRepository;
import com.pedromolon.reimburseflow.infrastructure.repository.SpringUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReimbursementRepositoryJpaAdapter implements ReimbursementRepository {

    private final SpringReimbursementRepository springReimbursementRepository;
    private final SpringUserRepository springUserRepository;

    public ReimbursementRepositoryJpaAdapter(SpringReimbursementRepository springReimbursementRepository, SpringUserRepository springUserRepository) {
        this.springReimbursementRepository = springReimbursementRepository;
        this.springUserRepository = springUserRepository;
    }


    @Override
    public Optional<Reimbursement> findById(Long id) {
        return springReimbursementRepository.findById(id)
                .map(EntityMapper::toDomain);
    }

    @Override
    public Reimbursement save(Reimbursement reimbursement) {
        UserEntity userEntity = springUserRepository.findById(reimbursement.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return EntityMapper.toDomain(
                springReimbursementRepository.save(
                        EntityMapper.toEntity(reimbursement, userEntity)
                )
        );
    }
}
