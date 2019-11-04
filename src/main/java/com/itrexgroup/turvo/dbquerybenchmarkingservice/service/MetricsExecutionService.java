package com.itrexgroup.turvo.dbquerybenchmarkingservice.service;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.QueryPerformanceHistory;

public interface MetricsExecutionService {
    void collectAndSaveMetrics(QueryPerformanceHistory history);
}
