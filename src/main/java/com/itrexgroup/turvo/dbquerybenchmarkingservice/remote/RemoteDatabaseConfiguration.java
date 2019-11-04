package com.itrexgroup.turvo.dbquerybenchmarkingservice.remote;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.google.common.collect.Maps.transformValues;

@Configuration
public class RemoteDatabaseConfiguration {
    @Bean
    public ImmutableMap<String, RemoteDatabase> databaseClients(
            @Autowired RemoteDatabaseConfigMap remoteDatabaseConfigMap) {
        return ImmutableMap.copyOf(transformValues(
                remoteDatabaseConfigMap.getRemotes(),
                this::toDatabaseClient));
    }

    private RemoteDatabaseClient toDatabaseClient(HikariConfig hikariConfig) {
        return new RemoteDatabaseClient(new JdbcTemplate(new HikariDataSource(hikariConfig)));
    }
}
