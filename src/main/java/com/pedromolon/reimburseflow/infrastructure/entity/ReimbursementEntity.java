package com.pedromolon.reimburseflow.infrastructure.entity;

import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_reimbursements")
public class ReimbursementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReimbursementCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReimbursementStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}
