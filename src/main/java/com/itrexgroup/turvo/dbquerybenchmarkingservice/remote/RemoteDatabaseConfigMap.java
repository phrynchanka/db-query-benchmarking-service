package com.itrexgroup.turvo.dbquerybenchmarkingservice.remote;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties(prefix = "db")
@Configuration
@Data
public class RemoteDatabaseConfigMap {
    private Map<String, HikariConfig> remotes;
}
