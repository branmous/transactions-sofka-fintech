package com.base.project.usecase.createtransaction;

import com.base.project.model.transaction.Transaction;
import com.base.project.model.transaction.gateways.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTransactionUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CreateTransactionUseCase createTransactionUseCase;

    @Captor
    private ArgumentCaptor<Mono<Transaction>> transactionMonoCaptor;

    private Transaction inputTransaction;

    @BeforeEach
    void setUp() {
        inputTransaction = Transaction.builder()
                .id(null) // ID is null for a new transaction
                .amount(BigDecimal.ZERO)
                .commission(null)
                .dateCreated(null)
                .build();
    }

    @Test
    @DisplayName("execute() should create transaction with low commission for amount <= 10000")
    void shouldCreateTransactionWithLowCommissionSuccessfully() {
        // Arrange
        inputTransaction = inputTransaction.toBuilder().amount(new BigDecimal("5000")).build();
        BigDecimal expectedCommission = new BigDecimal("100.00"); // 5000 * 0.02

        when(transactionRepository.save(any(Mono.class))).thenAnswer(invocation -> {
            Mono<Transaction> transactionMono = invocation.getArgument(0);
            return transactionMono.map(t -> t.toBuilder().id(1L).build());
        });

        // Act
        Mono<Transaction> result = createTransactionUseCase.execute(inputTransaction);

        // Assert
        StepVerifier.create(result)
                .assertNext(savedTransaction -> {
                    assertEquals(1L, savedTransaction.getId());
                    assertEquals(0, expectedCommission.compareTo(savedTransaction.getCommission()));
                    assertEquals(inputTransaction.getAmount(), savedTransaction.getAmount());
                })
                .verifyComplete();

        verify(transactionRepository).save(transactionMonoCaptor.capture());
        Mono<Transaction> capturedMono = transactionMonoCaptor.getValue();

        StepVerifier.create(capturedMono)
                .assertNext(t -> {
                    assertEquals(0, expectedCommission.compareTo(t.getCommission()));
                    // Ensure date is set
                    assert t.getDateCreated() != null;
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("execute() should create transaction with high commission for amount > 10000")
    void shouldCreateTransactionWithHighCommissionSuccessfully() {
        // Arrange
        inputTransaction = inputTransaction.toBuilder().amount(new BigDecimal("20000")).build();
        BigDecimal expectedCommission = new BigDecimal("1000.00"); // 20000 * 0.05

        when(transactionRepository.save(any(Mono.class))).thenAnswer(invocation -> {
            Mono<Transaction> transactionMono = invocation.getArgument(0);
            return transactionMono.map(t -> t.toBuilder().id(2L).build());
        });

        // Act
        Mono<Transaction> result = createTransactionUseCase.execute(inputTransaction);

        // Assert
        StepVerifier.create(result)
                .assertNext(savedTransaction -> {
                    assertEquals(2L, savedTransaction.getId());
                    assertEquals(0, expectedCommission.compareTo(savedTransaction.getCommission()));
                })
                .verifyComplete();

        verify(transactionRepository).save(transactionMonoCaptor.capture());
        Mono<Transaction> capturedMono = transactionMonoCaptor.getValue();
        StepVerifier.create(capturedMono)
                .assertNext(t -> assertEquals(0, expectedCommission.compareTo(t.getCommission())))
                .verifyComplete();
    }
    
    @Test
    @DisplayName("execute() should create transaction with low commission for amount exactly 10000")
    void shouldHandleTransactionWithAmountOfExactlyTenThousand() {
        // Arrange
        inputTransaction = inputTransaction.toBuilder().amount(new BigDecimal("10000")).build();
        BigDecimal expectedCommission = new BigDecimal("200.00"); // 10000 * 0.02

        when(transactionRepository.save(any(Mono.class))).thenAnswer(invocation -> {
            Mono<Transaction> transactionMono = invocation.getArgument(0);
            return transactionMono.map(t -> t.toBuilder().id(3L).build());
        });

        // Act
        Mono<Transaction> result = createTransactionUseCase.execute(inputTransaction);

        // Assert
        StepVerifier.create(result)
                .assertNext(savedTransaction -> {
                    assertEquals(3L, savedTransaction.getId());
                    assertEquals(0, expectedCommission.compareTo(savedTransaction.getCommission()));
                })
                .verifyComplete();

        verify(transactionRepository).save(transactionMonoCaptor.capture());
        Mono<Transaction> capturedMono = transactionMonoCaptor.getValue();
        StepVerifier.create(capturedMono)
                .assertNext(t -> assertEquals(0, expectedCommission.compareTo(t.getCommission())))
                .verifyComplete();
    }

    @Test
    @DisplayName("execute() should propagate error when repository fails")
    void shouldHandleErrorWhenRepositoryFailsToSave() {
        // Arrange
        inputTransaction = inputTransaction.toBuilder().amount(new BigDecimal("100")).build();
        RuntimeException dbError = new RuntimeException("DB Error");
        when(transactionRepository.save(any(Mono.class))).thenReturn(Mono.error(dbError));

        // Act
        Mono<Transaction> result = createTransactionUseCase.execute(inputTransaction);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && "DB Error".equals(throwable.getMessage()))
                .verify();

        verify(transactionRepository).save(any(Mono.class));
    }
}
