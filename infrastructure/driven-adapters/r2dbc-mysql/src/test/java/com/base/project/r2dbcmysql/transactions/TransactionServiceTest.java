package com.base.project.r2dbcmysql.transactions;

import com.base.project.model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionDataRepository repository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private TransactionData transactionData;

    @BeforeEach
    void setUp() {
        transaction = Transaction.builder()
                .id(1L)
                .amount(new BigDecimal("100.50"))
                .commission(new BigDecimal("1.00"))
                .dateCreated(LocalDateTime.now())
                .build();

        transactionData = new TransactionData();
        transactionData.setId(1L);
        transactionData.setAmount(new BigDecimal("100.50"));
        transactionData.setCommission(new BigDecimal("1.00"));
        transactionData.setDateCreated(transaction.getDateCreated());
    }

    @Test
    @DisplayName("save() should return saved transaction successfully")
    void saveHappyPath() {
        // Arrange
        Mono<Transaction> transactionMono = Mono.just(transaction);

        when(mapper.map(any(Transaction.class), any(Class.class))).thenReturn(transactionData);
        when(repository.save(any(TransactionData.class))).thenReturn(Mono.just(transactionData));
        when(mapper.map(any(TransactionData.class), any(Class.class))).thenReturn(transaction);

        // Act
        Mono<Transaction> result = transactionService.save(transactionMono);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(savedTransaction -> {
                    return savedTransaction.getId().equals(1L) &&
                           savedTransaction.getAmount().equals(new BigDecimal("100.50"));
                })
                .verifyComplete();
    }
    
    @Test
    @DisplayName("save() should return error when repository fails")
    void saveErrorPath() {
        // Arrange
        Mono<Transaction> transactionMono = Mono.just(transaction);
        RuntimeException dbError = new RuntimeException("Database error");

        when(mapper.map(any(Transaction.class), any(Class.class))).thenReturn(transactionData);
        when(repository.save(any(TransactionData.class))).thenReturn(Mono.error(dbError));

        // Act
        Mono<Transaction> result = transactionService.save(transactionMono);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                                                  throwable.getMessage().equals("Database error"))
                .verify();
    }

    @Test
    @DisplayName("findAll() should return a flux of transactions successfully")
    void findAllHappyPath() {
        // Arrange
        Transaction transaction2 = Transaction.builder().id(2L).build();
        TransactionData transactionData2 = new TransactionData();
        transactionData2.setId(2L);

        when(repository.findAll()).thenReturn(Flux.just(transactionData, transactionData2));
        when(mapper.map(transactionData, Transaction.class)).thenReturn(transaction);
        when(mapper.map(transactionData2, Transaction.class)).thenReturn(transaction2);

        // Act
        Flux<Transaction> result = transactionService.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(transaction)
                .expectNext(transaction2)
                .verifyComplete();
    }

    @Test
    @DisplayName("findAll() should return empty flux when no transactions exist")
    void findAllEmpty() {
        // Arrange
        when(repository.findAll()).thenReturn(Flux.empty());

        // Act
        Flux<Transaction> result = transactionService.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }
    
    @Test
    @DisplayName("findAll() should return error when repository fails")
    void findAllErrorPath() {
        // Arrange
        RuntimeException dbError = new RuntimeException("Database error on findAll");
        when(repository.findAll()).thenReturn(Flux.error(dbError));

        // Act
        Flux<Transaction> result = transactionService.findAll();

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                                                  throwable.getMessage().equals("Database error on findAll"))
                .verify();
    }
    
    @Test
    @DisplayName("findById() should return a mono of transaction successfully")
    void findByIdHappyPath() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Mono.just(transactionData));
        when(mapper.map(transactionData, Transaction.class)).thenReturn(transaction);

        // Act
        Mono<Transaction> result = transactionService.findById(1L);

        // Assert
        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById() should return empty mono when no transaction exists")
    void findByIdEmpty() {
        // Arrange
        when(repository.findById(any(Long.class))).thenReturn(Mono.empty());

        // Act
        Mono<Transaction> result = transactionService.findById(99L);

        // Assert
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }
}
