package com.base.project.usecase.findalltransactions;

import com.base.project.model.transaction.Transaction;
import com.base.project.model.transaction.gateways.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllTransactionsUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FindAllTransactionsUseCase findAllTransactionsUseCase;

    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void setUp() {
        transaction1 = Transaction.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100.00))
                .commission(BigDecimal.valueOf(1.00))
                .dateCreated(LocalDateTime.now())
                .build();

        transaction2 = Transaction.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200.00))
                .commission(BigDecimal.valueOf(2.00))
                .dateCreated(LocalDateTime.now().plusHours(1))
                .build();
    }

    @Test
    @DisplayName("findAll() should return a flux of transactions successfully")
    void shouldReturnFluxOfTransactionsWhenFindAllIsCalled() {
        // Arrange
        when(transactionRepository.findAll()).thenReturn(Flux.just(transaction1, transaction2));

        // Act
        Flux<Transaction> result = findAllTransactionsUseCase.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(transaction1)
                .expectNext(transaction2)
                .verifyComplete();

        verify(transactionRepository).findAll();
    }

    @Test
    @DisplayName("findAll() should return an empty flux when no transactions exist")
    void shouldReturnEmptyFluxWhenNoTransactionsExist() {
        // Arrange
        when(transactionRepository.findAll()).thenReturn(Flux.empty());

        // Act
        Flux<Transaction> result = findAllTransactionsUseCase.findAll();

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(transactionRepository).findAll();
    }

    @Test
    @DisplayName("findAll() should propagate an error when the repository fails")
    void shouldPropagateErrorWhenRepositoryFails() {
        // Arrange
        RuntimeException exception = new RuntimeException("Database connection failed");
        when(transactionRepository.findAll()).thenReturn(Flux.error(exception));

        // Act
        Flux<Transaction> result = findAllTransactionsUseCase.findAll();

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(transactionRepository).findAll();
    }
}
