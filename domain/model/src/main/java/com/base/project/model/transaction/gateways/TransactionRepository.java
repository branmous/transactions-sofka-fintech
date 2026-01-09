package com.base.project.model.transaction.gateways;

import com.base.project.model.transaction.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Transaction> save(Mono<Transaction> transaction);
    Flux<Transaction> findAll();
}
