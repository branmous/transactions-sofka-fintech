package com.base.project.r2dbcmysql.config.properties;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class ConnectionDBConfigDTO {

    private String host;
    private int port;
}
