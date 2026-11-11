package com.pedromolon.reimburseflow.domain;

import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ReimbursementTest {

    @Test
    @DisplayName("Deve inicializar com status DRAFT e data preenchida")
    void shouldInitializeAsDraft() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        assertEquals(ReimbursementStatus.DRAFT, reimbursement.getStatus());
        assertNull(reimbursement.getId());
        assertNotNull(reimbursement.getCreatedAt());
    }

    @Test
    @DisplayName("Deve permitir submeter um reembolso que está em DRAFT")
    void shouldSubmitSuccessfully() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        reimbursement.submit();
        assertEquals(ReimbursementStatus.SUBMITTED, reimbursement.getStatus());
    }

    @Test
    @DisplayName("Não deve permitir submeter um reembolso que não esteja em DRAFT")
    void shouldNotAllowSubmitIfNotDraft() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        reimbursement.submit();

        IllegalStateException exception = assertThrows(IllegalStateException.class, reimbursement::submit);
        assertEquals("Reimbursement can just be submitted in DRAFT status", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aprovar o reembolso se estiver submetido")
    void shouldApproveReimbursementSubmittedStatus() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        reimbursement.submit();

        reimbursement.approve();

        assertEquals(ReimbursementStatus.APPROVED, reimbursement.getStatus());
    }

    @Test
    @DisplayName("Não deve aprovar o reembolso se não estiver submetido")
    void shouldThrowExceptionIfNotSubmitted() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        reimbursement.submit();

        reimbursement.approve();

        IllegalStateException exception = assertThrows(IllegalStateException.class, reimbursement::approve);
        assertEquals("Reimbursement can just be approved in SUBMITTED status", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar reembolso informando justificativa obrigatória")
    void shouldRejectWithComment() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        reimbursement.submit();

        reimbursement.reject("Nota fiscal ilegível");

        assertEquals(ReimbursementStatus.REJECTED, reimbursement.getStatus());
    }

    @Test
    @DisplayName("Não deve permitir rejeitar um reembolso sem justificativa")
    void shouldNotRejectWithoutComment() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        reimbursement.submit();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reimbursement.reject(""));

        assertEquals("Comment cannot be null or blank", exception.getMessage());
    }

    @Test
    @DisplayName("Deve permitir pagar um reembolso que esteja aprovado")
    void shouldPayReimbursementApprovedStatus() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        reimbursement.submit();

        reimbursement.approve();

        reimbursement.pay();

        assertEquals(ReimbursementStatus.PAID, reimbursement.getStatus());
    }

    @Test
    @DisplayName("Não deve permitir pagar um reembolso que não esteja aprovado")
    void shouldNotPayWithoutApprovedStatus() {
        Reimbursement reimbursement = new Reimbursement(
                1L,
                "Hotel",
                new BigDecimal("300.00"),
                ReimbursementCategory.OTHER
        );

        reimbursement.submit();

        IllegalStateException exception = assertThrows(IllegalStateException.class, reimbursement::pay);
        assertEquals("Reimbursement can just be paid in APPROVED status", exception.getMessage());
    }

}
