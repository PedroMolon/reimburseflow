package com.pedromolon.reimburseflow.infrastructure.web.controller;

import com.pedromolon.reimburseflow.application.service.ReimbursementService;
import com.pedromolon.reimburseflow.infrastructure.web.dto.ReimbursementRequestDTO;
import com.pedromolon.reimburseflow.infrastructure.web.dto.ReimbursementResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reimbursement")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ReimbursementResponseDTO> create(
            @RequestBody @Valid ReimbursementRequestDTO request,
            @AuthenticationPrincipal Jwt jwt
            ) {
        String userEmail = jwt.getClaimAsString("email");
        ReimbursementResponseDTO response = reimbursementService.create(request, userEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<ReimbursementResponseDTO>> findAll() {
        List<ReimbursementResponseDTO> response = reimbursementService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{id}/submit")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_MENAGER')")
    public ResponseEntity<Void> submit(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userEmail = jwt.getClaimAsString("email");
        reimbursementService.submit(id, userEmail);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ROLE_MENAGER')")
    public ResponseEntity<Void> approve(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String managerEmail = jwt.getClaimAsString("email");
        reimbursementService.approve(id, managerEmail);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ROLE_MENAGER')")
    public ResponseEntity<Void> reject(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String managerEmail = jwt.getClaimAsString("email");
        reimbursementService.reject(id, managerEmail);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/pay")
    @PreAuthorize("hasRole('ROLE_FINANCIAL')")
    public ResponseEntity<Void> pay(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String financialEmail = jwt.getClaimAsString("email");
        reimbursementService.pay(id, financialEmail);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
