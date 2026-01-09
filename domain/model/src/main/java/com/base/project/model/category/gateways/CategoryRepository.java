package com.base.project.model.category.gateways;

import com.base.project.model.category.Category;
import reactor.core.publisher.Flux;

public interface CategoryRepository {

    Flux<Category> findAll();
}
