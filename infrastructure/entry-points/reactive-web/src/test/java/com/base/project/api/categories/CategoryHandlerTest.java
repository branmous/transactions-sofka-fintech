package com.base.project.api.categories;

import com.base.project.model.category.Category;
import com.base.project.usecase.createphotos.CreatePhotosUseCase;
import com.base.project.usecase.findallcategories.FindAllCategoriesUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = {CategoryRouter.class, CategoryHandler.class})
@WebFluxTest
class CategoryHandlerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FindAllCategoriesUseCase useCase;

    @MockBean
    private CreatePhotosUseCase createPhotosUseCase;

    @Test
    public void testGetAllCategories() {
        Category category1 = new Category(1L, "Category1");
        Category category2 = new Category(2L, "Category2");

        when(useCase.findAll()).thenReturn(Flux.just(category1, category2));

        webTestClient.get().uri("/api/categories")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(2);

        verify(useCase).findAll();
    }
}