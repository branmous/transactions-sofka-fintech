package com.base.project.r2dbcmysql.transactions;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransactionDataRepository extends
        ReactiveCrudRepository<TransactionData, Long>,
        ReactiveQueryByExampleExecutor<TransactionData> {
}
