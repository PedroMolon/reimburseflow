package com.pedromolon.reimburseflow.application.mapper;

import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;
import com.pedromolon.reimburseflow.infrastructure.entity.ReimbursementEntity;
import com.pedromolon.reimburseflow.infrastructure.entity.UserEntity;
import com.pedromolon.reimburseflow.infrastructure.web.dto.ReimbursementRequestDTO;
import com.pedromolon.reimburseflow.infrastructure.web.dto.ReimbursementResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReimbursementMapper {

    public ReimbursementEntity toEntity(ReimbursementRequestDTO request, UserEntity user) {
        ReimbursementEntity entity = new ReimbursementEntity();
        entity.setUser(user);
        entity.setDescription(request.description());
        entity.setAmount(request.amount());
        entity.setCategory(ReimbursementCategory.valueOf(request.category()));
        entity.setStatus(ReimbursementStatus.DRAFT);
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public static ReimbursementResponseDTO toResponse(ReimbursementEntity reimbursementEntity) {
        return new ReimbursementResponseDTO(
                reimbursementEntity.getId(),
                reimbursementEntity.getDescription(),
                reimbursementEntity.getAmount(),
                reimbursementEntity.getCategory().toString(),
                reimbursementEntity.getStatus().toString(),
                reimbursementEntity.getUser().getEmail(),
                reimbursementEntity.getCreatedAt()
        );
    }

}
