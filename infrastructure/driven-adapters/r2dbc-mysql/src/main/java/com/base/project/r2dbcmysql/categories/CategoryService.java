package com.base.project.r2dbcmysql.categories;

import com.base.project.model.category.Category;
import com.base.project.model.category.gateways.CategoryRepository;
import com.base.project.r2dbcmysql.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryService extends
        ReactiveAdapterOperations<Category, CategoryData, Long, CategoryDataRepository>
        implements CategoryRepository {
    protected CategoryService(CategoryDataRepository repository,
                              ObjectMapper mapper) {
        super(repository, mapper, data -> mapper.map(data, Category.class));
    }
}
