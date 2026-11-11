package com.pedromolon.reimburseflow.usecase;

import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.User;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementStatus;
import com.pedromolon.reimburseflow.domain.gateway.ReimbursementRepository;
import com.pedromolon.reimburseflow.domain.gateway.UserRepository;
import com.pedromolon.reimburseflow.usecase.dto.CreateReimbursement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateReimbursementUseCaseTest {

    @Mock
    private ReimbursementRepository reimbursementRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateReimbursementUseCase createReimbursementUseCase;

    @Test
    @DisplayName("Deve criar um reembolso com sucesso no status DRAFT quando o usuário existir")
    public void shouldCreateReimbursementSuccessfully() {
        Long userId = 1L;
        var command = new CreateReimbursement(
                userId,
                "Viagem de negócios para São Paulo",
                new BigDecimal("1500.00"),
                ReimbursementCategory.TRAVEL
        );

        User mockUser = new User(userId, "Pedro", "pedro@email.com", "password", null, null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(reimbursementRepository.save(any(Reimbursement.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reimbursement result = createReimbursementUseCase.execute(command);

        assertNotNull(result);
        assertEquals(ReimbursementStatus.DRAFT, result.getStatus());
        assertEquals(userId, result.getUserId());
        assertEquals("Viagem de negócios para São Paulo", result.getDescription());
        assertEquals(ReimbursementCategory.TRAVEL, result.getCategory());

        verify(userRepository, times(1)).findById(userId);
        verify(reimbursementRepository, times(1)).save(any(Reimbursement.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar reembolso para um usuário que não existe")
    public void shouldThrowExceptionWhenUserNotFound() {
        Long invalidUserId = 99L;
        var command = new CreateReimbursement(
                invalidUserId,
                "Viagem de negócios para São Paulo",
                new BigDecimal("1500.00"),
                ReimbursementCategory.TRAVEL
        );

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> createReimbursementUseCase.execute(command));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(invalidUserId);
        verify(reimbursementRepository, times(0)).save(any(Reimbursement.class));
    }

}
