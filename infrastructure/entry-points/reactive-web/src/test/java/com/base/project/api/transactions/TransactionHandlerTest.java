package com.base.project.api.transactions;

import com.base.project.model.transaction.Transaction;
import com.base.project.usecase.createtransaction.CreateTransactionUseCase;
import com.base.project.usecase.findalltransactions.FindAllTransactionsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {TransactionRouter.class, TransactionHandler.class})
class TransactionHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreateTransactionUseCase createTransactionUseCase;

    @MockBean
    private FindAllTransactionsUseCase findAllTransactionsUseCase;

    @Test
    void testGetAllTransactions() {
        // Arrange
        Transaction transaction1 = new Transaction(1L, new BigDecimal("100.00"), new BigDecimal("2.00"), LocalDateTime.now());
        Transaction transaction2 = new Transaction(2L, new BigDecimal("200.00"), new BigDecimal("4.00"), LocalDateTime.now());
        when(findAllTransactionsUseCase.findAll()).thenReturn(Flux.just(transaction1, transaction2));

        // Act & Assert
        webTestClient.get().uri("/api/v1/transactions")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Transaction.class)
                .hasSize(2)
                .contains(transaction1, transaction2);

        verify(findAllTransactionsUseCase).findAll();
    }

    @Test
    void testCreateTransaction() {
        // Arrange
        TransactionRequest request = TransactionRequest.builder()
                .amount(new BigDecimal("1000.00"))
                .build();

        Transaction createdTransaction = Transaction.builder()
                .id(1L)
                .amount(new BigDecimal("1000.00"))
                .commission(new BigDecimal("20.00"))
                .dateCreated(LocalDateTime.now())
                .build();

        when(createTransactionUseCase.execute(any(Transaction.class))).thenReturn(Mono.just(createdTransaction));

        // Act & Assert
        webTestClient.post().uri("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("Location", "/api/v1/transactions/1")
                .expectBody(Transaction.class)
                .isEqualTo(createdTransaction);

        verify(createTransactionUseCase).execute(any(Transaction.class));
    }
}
