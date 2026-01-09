package com.base.project.r2dbcmysql.config;


import com.base.project.r2dbcmysql.config.properties.MySqlProperties;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Slf4j
@Configuration
@EnableR2dbcRepositories
@RequiredArgsConstructor
public class h2DataBaseConfiguration extends AbstractR2dbcConfiguration {

    private final MySqlProperties properties;

    @Override
    @Bean
    @SneakyThrows
    public ConnectionFactory connectionFactory() {
        val password =  properties.getPassword();
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        .inMemory(properties.getDb())
                        .username( properties.getUsername())
                        .password(password)
                        .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                        .build()
        );
    }
}
