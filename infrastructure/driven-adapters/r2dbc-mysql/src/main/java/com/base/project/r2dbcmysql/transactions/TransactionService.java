package com.base.project.r2dbcmysql.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionDataRepository repository;

    public Flux<TransactionData> findAll() {
        return repository.findAll();
    }

    public Mono<TransactionData> findById(Long id) {
        return repository.findById(id);
    }

    public Mono<TransactionData> save(TransactionData transaction) {
        return repository.save(transaction);
    }

    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}
