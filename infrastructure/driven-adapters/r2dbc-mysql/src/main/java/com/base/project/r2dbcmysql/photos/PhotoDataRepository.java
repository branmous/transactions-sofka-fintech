package com.base.project.r2dbcmysql.photos;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PhotoDataRepository extends ReactiveCrudRepository<PhotoData, Long>,
        ReactiveQueryByExampleExecutor<PhotoData> {
}
