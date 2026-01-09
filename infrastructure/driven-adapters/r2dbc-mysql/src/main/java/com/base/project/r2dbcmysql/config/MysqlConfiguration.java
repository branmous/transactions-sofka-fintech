package com.base.project.r2dbcmysql.config;


import com.base.project.r2dbcmysql.config.properties.MySqlProperties;
import io.r2dbc.pool.PoolingConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Duration;

import static com.base.project.r2dbcmysql.config.utils.DBConfigUtils.splitHostAndPort;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.PROTOCOL;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

@Slf4j
@Configuration
@EnableR2dbcRepositories
@RequiredArgsConstructor
public class MysqlConfiguration extends AbstractR2dbcConfiguration {

    private final MySqlProperties properties;

    @Override
    @Bean
    @SneakyThrows
    public ConnectionFactory connectionFactory() {
        val hostAndPort = properties.getHostAndPort();
        val password =  properties.getPassword();
        val connectionDBConfigDTO = splitHostAndPort(hostAndPort);
        val connectionPool = properties.getConnectionPool();
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, properties.getDriver())
                .option(PROTOCOL, properties.getProtocol())
                .option(PoolingConnectionFactoryProvider.ACQUIRE_RETRY, connectionPool.getAcquireRetry())
                .option(PoolingConnectionFactoryProvider.MIN_IDLE, connectionPool.getMinIdle())
                .option(PoolingConnectionFactoryProvider.MAX_IDLE_TIME, Duration.ofMinutes(connectionPool.getMaxIdleTime()))
                .option(PoolingConnectionFactoryProvider.BACKGROUND_EVICTION_INTERVAL,
                        Duration.ofMinutes(connectionPool.getBackgroundEvictionInterval()))
                .option(PoolingConnectionFactoryProvider.VALIDATION_QUERY, connectionPool.getValidationQuery())
                .option(HOST, connectionDBConfigDTO.getHost())
                .option(PORT, connectionDBConfigDTO.getPort())
                .option(USER, properties.getUsername())
                .option(PASSWORD, password)
                .option(DATABASE, properties.getDb())

                .build());
    }
}
