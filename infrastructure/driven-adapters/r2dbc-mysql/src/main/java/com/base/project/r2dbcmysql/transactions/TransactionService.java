package com.base.project.r2dbcmysql.transactions;

import com.base.project.model.transaction.Transaction;
import com.base.project.model.transaction.gateways.TransactionRepository;
import com.base.project.r2dbcmysql.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TransactionService extends ReactiveAdapterOperations<Transaction, TransactionData, Long, TransactionDataRepository>
        implements TransactionRepository {

    protected TransactionService(TransactionDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, data -> mapper.map(data, Transaction.class));
    }

    public Mono<Transaction> save(Mono<Transaction> transaction) {
        return transaction
                .map(this::toData)
                .flatMap(this::saveData)
                .map(this::toEntity);
    }
}
