package com.pedromolon.reimburseflow.infrastructure.repository;

import com.pedromolon.reimburseflow.infrastructure.entity.ReimbursementHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringReimbursementHistoryRepository extends JpaRepository<ReimbursementHistoryEntity, Long> {
}
