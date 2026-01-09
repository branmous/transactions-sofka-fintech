package com.base.project.config;

import com.base.project.r2dbcmysql.config.properties.MySqlProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mysql.datasource")

public class MySqlProps extends MySqlProperties {
}
