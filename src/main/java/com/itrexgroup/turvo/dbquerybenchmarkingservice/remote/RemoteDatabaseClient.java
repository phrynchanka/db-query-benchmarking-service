package com.itrexgroup.turvo.dbquerybenchmarkingservice.remote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public final class RemoteDatabaseClient implements RemoteDatabase {
    private final JdbcTemplate jdbcTemplate;

    public RemoteDatabaseClient(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void execute(String sql) throws RemoteDatabaseInvocationException {
        try {
            jdbcTemplate.execute(sql);
        } catch (DataAccessException e) {
            throw new RemoteDatabaseInvocationException("Failed to execute sql", e);
        }

    }
}
