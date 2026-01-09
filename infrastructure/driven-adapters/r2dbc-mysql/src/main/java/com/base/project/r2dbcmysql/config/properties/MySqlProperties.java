package com.base.project.r2dbcmysql.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MySqlProperties {
    private String hostAndPort;
    private String username;
    private String password;
    private String db;
    private String driver;
    private String protocol;
    private ConnectionPoolDBProperties connectionPool;
}
