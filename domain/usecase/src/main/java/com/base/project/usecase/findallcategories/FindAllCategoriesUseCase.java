package com.base.project.usecase.findallcategories;

import com.base.project.model.category.Category;
import com.base.project.model.category.gateways.CategoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindAllCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    public Flux<Category> findAll() {
        return categoryRepository.findAll();
    }
}
