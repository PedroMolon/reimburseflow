package com.pedromolon.reimburseflow.infrastructure.entity;

import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_reimbursement_histories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReimbursementHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reimbursement_id", nullable = false)
    private ReimbursementEntity reimbursement;

    @ManyToOne
    @JoinColumn(name = "changed_by_user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private ReimbursementStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private ReimbursementStatus newStatus;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

}
