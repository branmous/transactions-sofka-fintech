package com.base.project.config;

import com.base.project.model.transaction.gateways.TransactionRepository;
import com.base.project.usecase.createtransaction.CreateTransactionUseCase;
import com.base.project.usecase.findalltransactions.FindAllTransactionsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public CreateTransactionUseCase buildCreateTransactionUseCase(final TransactionRepository transactionRepository) {
        return new CreateTransactionUseCase(transactionRepository);
    }

    @Bean
    public FindAllTransactionsUseCase buildFindAllTransactionsUseCase(TransactionRepository transactionRepository) {
        return new FindAllTransactionsUseCase(transactionRepository);
    }
}
