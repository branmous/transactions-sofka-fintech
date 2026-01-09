package com.base.project.r2dbcmysql.transactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionData {
    @Id
    private Long id;
    private BigDecimal amount;
    private BigDecimal commission;
    @Column(value = "date_created")
    private LocalDateTime dateCreated;
}
