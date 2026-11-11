package com.pedromolon.reimburseflow.infrastructure.repository;

import com.pedromolon.reimburseflow.infrastructure.entity.ReimbursementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringReimbursementRepository extends JpaRepository<ReimbursementEntity, Long> {
    List<ReimbursementEntity> findByUserId(Long userId);
}
