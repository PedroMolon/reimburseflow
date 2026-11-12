package com.pedromolon.reimburseflow.application.service;

import com.pedromolon.reimburseflow.application.mapper.ReimbursementMapper;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;
import com.pedromolon.reimburseflow.infrastructure.entity.ReimbursementEntity;
import com.pedromolon.reimburseflow.infrastructure.entity.ReimbursementHistoryEntity;
import com.pedromolon.reimburseflow.infrastructure.entity.UserEntity;
import com.pedromolon.reimburseflow.infrastructure.repository.SpringReimbursementHistoryRepository;
import com.pedromolon.reimburseflow.infrastructure.repository.SpringReimbursementRepository;
import com.pedromolon.reimburseflow.infrastructure.repository.SpringUserRepository;
import com.pedromolon.reimburseflow.infrastructure.web.dto.ReimbursementRequestDTO;
import com.pedromolon.reimburseflow.infrastructure.web.dto.ReimbursementResponseDTO;
import com.pedromolon.reimburseflow.infrastructure.web.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReimbursementService {

    private final SpringReimbursementRepository reimbursementRepository;
    private final SpringUserRepository userRepository;
    private final ReimbursementMapper reimbursementMapper;
    private final SpringReimbursementHistoryRepository reimbursementHistoryRepository;

    public ReimbursementService(SpringReimbursementRepository reimbursementRepository, SpringUserRepository userRepository, ReimbursementMapper reimbursementMapper, SpringReimbursementHistoryRepository reimbursementHistoryRepository) {
        this.reimbursementRepository = reimbursementRepository;
        this.userRepository = userRepository;
        this.reimbursementMapper = reimbursementMapper;
        this.reimbursementHistoryRepository = reimbursementHistoryRepository;
    }

    @Transactional
    public ReimbursementResponseDTO create(ReimbursementRequestDTO request, String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ReimbursementEntity entity = reimbursementMapper.toEntity(request, user);
        ReimbursementEntity savedEntity = reimbursementRepository.save(entity);

        return ReimbursementMapper.toResponse(savedEntity);
    }

    public List<ReimbursementResponseDTO> findAll() {
        return reimbursementRepository.findAll()
                .stream()
                .map(ReimbursementMapper::toResponse)
                .toList();
    }

    @Transactional
    public void submit(Long id, String userEmail) {
        ReimbursementEntity reimbursementEntity = findReimbursementOrThrow(id);

        validateUser(reimbursementEntity, userEmail);
        validateStatus(reimbursementEntity, ReimbursementStatus.DRAFT, "Only DRAFT reimbursements can be submitted");

        updateStatusAndSaveHistory(reimbursementEntity, ReimbursementStatus.SUBMITTED, userEmail, "Reimbursement submitted for review");
    }

    @Transactional
    public void approve(Long id, String managerEmail) {
        ReimbursementEntity reimbursementEntity = findReimbursementOrThrow(id);

        validateStatus(reimbursementEntity, ReimbursementStatus.SUBMITTED, "Only SUBMITTED reimbursements can be approved");

        updateStatusAndSaveHistory(reimbursementEntity, ReimbursementStatus.APPROVED, managerEmail, "Reimbursement approved by manager");
    }

    @Transactional
    public void reject(Long id, String managerEmail) {
        ReimbursementEntity reimbursementEntity = findReimbursementOrThrow(id);

        validateStatus(reimbursementEntity, ReimbursementStatus.SUBMITTED, "Only SUBMITTED reimbursements can be rejected");

        updateStatusAndSaveHistory(reimbursementEntity, ReimbursementStatus.REJECTED, managerEmail, "Reimbursement rejected by manager");
    }

    @Transactional
    public void pay(Long id, String financialEmail) {
        ReimbursementEntity reimbursementEntity = findReimbursementOrThrow(id);

        validateStatus(reimbursementEntity, ReimbursementStatus.APPROVED, "Only APPROVED reimbursements can be paid");

        updateStatusAndSaveHistory(reimbursementEntity, ReimbursementStatus.PAID, financialEmail, "Reimbursement paid by financial");
    }

    private ReimbursementEntity findReimbursementOrThrow(Long id) {
        return reimbursementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reimbursement not found"));
    }

    public void validateUser(ReimbursementEntity reimbursementEntity, String userEmail) {
        if (!reimbursementEntity.getUser().getEmail().equals(userEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only the user who created the reimbursement can submit it");
        }
    }

    public void validateStatus(ReimbursementEntity reimbursementEntity, ReimbursementStatus status, String errorMessage) {
        if (!reimbursementEntity.getStatus().equals(status)) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    private void updateStatusAndSaveHistory(ReimbursementEntity reimbursement, ReimbursementStatus newStatus, String userEmail, String comment) {
        ReimbursementStatus previousStatus = reimbursement.getStatus();

        reimbursement.setStatus(newStatus);
        reimbursementRepository.save(reimbursement);

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ReimbursementHistoryEntity reimbursementHistory = new ReimbursementHistoryEntity();
        reimbursementHistory.setReimbursement(reimbursement);
        reimbursementHistory.setUser(user);
        reimbursementHistory.setPreviousStatus(previousStatus);
        reimbursementHistory.setNewStatus(newStatus);
        reimbursementHistory.setComment(comment);
        reimbursement.setCreatedAt(LocalDateTime.now());

        reimbursementHistoryRepository.save(reimbursementHistory);
    }

}
