
package com.itrexgroup.turvo.dbquerybenchmarkingservice.service;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.repository.QueryPerformanceHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus.CREATED;

@Service
@Slf4j
public class MetricsScheduledServiceImpl implements MetricsScheduledService {
    @Autowired
    private MetricsExecutionService executionService;

    @Autowired
    private QueryPerformanceHistoryRepository historyRepository;

    @Override
    @Scheduled(fixedDelayString = "${poller.fixed-delay}")
    public void pollQueriesToMeasure() {
        log.info("Start queries polling.");

        historyRepository.
                findTop100ByStatus(CREATED).
                parallelStream().
                forEach(executionService::collectAndSaveMetrics);

        log.info("Finished queries polling.");
    }
}
