package com.base.project.usecase.createtransaction;

import com.base.project.model.transaction.Transaction;
import com.base.project.model.transaction.gateways.TransactionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CreateTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public Mono<Transaction> execute(Transaction transaction) {
        return Mono.just(transaction)
                .map(this::calculateCommission)
                .flatMap(transactionRepository::save);
    }

    private Mono<Transaction> calculateCommission(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        BigDecimal commission;

        if (amount.compareTo(BigDecimal.valueOf(10000)) > 0) {
            commission = amount.multiply(BigDecimal.valueOf(0.05));
        } else {
            commission = amount.multiply(BigDecimal.valueOf(0.02));
        }

        return Mono.just(transaction.toBuilder()
                .commission(commission)
                .date(LocalDateTime.now())
                .build());
    }
}
