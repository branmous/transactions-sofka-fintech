package com.base.project.api.transactions;

import com.base.project.model.transaction.Transaction;
import com.base.project.usecase.createtransaction.CreateTransactionUseCase;
import com.base.project.usecase.findalltransactions.FindAllTransactionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TransactionHandler {

    private final CreateTransactionUseCase useCase;
    private final FindAllTransactionsUseCase findAllTransactionsUseCase;

    public Mono<ServerResponse> createTransaction(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TransactionRequest.class)
                .map(request -> Transaction.builder().amount(request.getAmount()).build())
                .flatMap(useCase::execute)
                .flatMap(transaction -> ServerResponse.created(URI.create("/api/v1/transactions/" + transaction.getId()))
                        .bodyValue(transaction));
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return findAllTransactionsUseCase.findAll()
                .collectList()
                .flatMap(transactions -> ServerResponse.ok().bodyValue(transactions));
    }
}
