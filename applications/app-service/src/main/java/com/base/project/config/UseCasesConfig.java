package com.base.project.config;

import com.base.project.model.category.gateways.CategoryRepository;
import com.base.project.model.photo.gateways.PhotoRepository;
import com.base.project.model.photo.gateways.PhotoRestRepository;
import com.base.project.model.transaction.gateways.TransactionRepository;
import com.base.project.usecase.createphotos.CreatePhotosUseCase;
import com.base.project.usecase.createtransaction.CreateTransactionUseCase;
import com.base.project.usecase.findallcategories.FindAllCategoriesUseCase;
import com.base.project.usecase.findalltransactions.FindAllTransactionsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public FindAllCategoriesUseCase buildFindAllCategoriesUseCase(final CategoryRepository categoryRepository) {
        return new FindAllCategoriesUseCase(categoryRepository);
    }

    @Bean
    public CreatePhotosUseCase buildCreatePhotosUseCase(final PhotoRestRepository photoRestRepository,
                                                        final PhotoRepository photoRepository) {
        return new CreatePhotosUseCase(photoRestRepository, photoRepository);
    }

    @Bean
    public CreateTransactionUseCase buildCreateTransactionUseCase(final TransactionRepository transactionRepository) {
        return new CreateTransactionUseCase(transactionRepository);
    }

    @Bean
    public FindAllTransactionsUseCase buildFindAllTransactionsUseCase(TransactionRepository transactionRepository) {
        return new FindAllTransactionsUseCase(transactionRepository);
    }
}
