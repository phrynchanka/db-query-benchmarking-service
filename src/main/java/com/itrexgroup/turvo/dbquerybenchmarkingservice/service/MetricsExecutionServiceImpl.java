package com.itrexgroup.turvo.dbquerybenchmarkingservice.service;

import com.google.common.collect.ImmutableMap;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.QueryPerformance;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.QueryPerformanceHistory;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.remote.RemoteDatabase;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.remote.RemoteDatabaseInvocationException;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.repository.QueryPerformanceHistoryRepository;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.service.metrics.MetricsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus.ERROR;
import static com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus.SUCCESS;
import static com.itrexgroup.turvo.dbquerybenchmarkingservice.service.metrics.PerformanceMetrics.createMetrics;

@Slf4j
@Service
public class MetricsExecutionServiceImpl implements MetricsExecutionService {
    private final static String ERROR_MSG_DELIMITER = ": ";

    @Autowired
    private ImmutableMap<String, RemoteDatabase> remoteDatabases;

    @Autowired
    private QueryPerformanceHistoryRepository historyRepository;

    @Async
    @Override
    public void collectAndSaveMetrics(QueryPerformanceHistory history) {
        QueryPerformance queryPerformance = history.getQueryPerformance();
        String sql = queryPerformance.getQuery().getSql();
        String version = queryPerformance.getQuery().getVersion();
        String queryName = queryPerformance.getQuery().getQueryName();

        MetricsResult metricsResult = MetricsResult.builder().status(SUCCESS).build();
        try {
            log.info("Start metric collecting: {} - {}: {}", queryName, version, sql);

            metricsResult = createMetrics(history.getQueryPerformance().getType()).
                    collectMetrics(remoteDatabases.get(queryPerformance.getDatabase()), sql);

            log.info("Finish metric collecting: {} - {}: {}", queryName, version, sql);
        } catch (RemoteDatabaseInvocationException e) {
            String errorMessage = e.getMessage();
            String causeMessage = e.getCause().getMessage();
            metricsResult.setStatus(ERROR);
            metricsResult.setResult(errorMessage + ERROR_MSG_DELIMITER + causeMessage);

            log.info("Failed metric collecting: {} - {}: {}, {}:{}",
                    queryName, version, sql,
                    errorMessage, causeMessage);
        }
        updateHistoryRecord(metricsResult, history);
    }

    private void updateHistoryRecord(MetricsResult metricsResult, QueryPerformanceHistory history) {
        history.setUpdated(new Date());
        history.setResult(metricsResult.getResult());
        history.setStatus(metricsResult.getStatus());
        historyRepository.save(history);
    }
}
