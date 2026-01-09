package com.base.project.r2dbcmysql.config.utils;

import com.base.project.r2dbcmysql.config.properties.ConnectionDBConfigDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DBConfigUtils {

    private DBConfigUtils() {
    }

    public static ConnectionDBConfigDTO splitHostAndPort(String hostAndPort) {
        ConnectionDBConfigDTO connectionDBConfigDTO = null;
        try {
            if (hostAndPort.contains(":") && hostAndPort.split(":").length == 2) {
                String[] configurationValues = hostAndPort.split(":");
                connectionDBConfigDTO = ConnectionDBConfigDTO.builder()
                        .host(configurationValues[0])
                        .port(Integer.parseInt(configurationValues[1]))
                        .build();
            }
        } catch (Exception exception) {
            log.error("Unable to get host and port database configuration parameters from yml property hostAndPort", exception);
            throw exception;
        }
        return connectionDBConfigDTO;
    }
}
