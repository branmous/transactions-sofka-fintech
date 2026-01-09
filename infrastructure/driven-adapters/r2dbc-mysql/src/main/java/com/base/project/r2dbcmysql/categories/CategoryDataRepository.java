package com.base.project.r2dbcmysql.categories;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryDataRepository extends
        ReactiveCrudRepository<CategoryData, Long>,
        ReactiveQueryByExampleExecutor<CategoryData> {
}
