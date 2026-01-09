package com.base.project.r2dbcmysql.categories;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class CategoryData {
    @Id
    private Long id;
    @Column(value = "description")
    private String description;
}
