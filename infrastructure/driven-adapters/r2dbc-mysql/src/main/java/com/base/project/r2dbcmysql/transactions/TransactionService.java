package com.base.project.r2dbcmysql.transactions;

import com.base.project.model.photo.Photo;
import com.base.project.model.transaction.Transaction;
import com.base.project.model.transaction.gateways.TransactionRepository;
import com.base.project.r2dbcmysql.helper.ReactiveAdapterOperations;
import com.base.project.r2dbcmysql.photos.PhotoData;
import com.base.project.r2dbcmysql.photos.PhotoDataRepository;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
