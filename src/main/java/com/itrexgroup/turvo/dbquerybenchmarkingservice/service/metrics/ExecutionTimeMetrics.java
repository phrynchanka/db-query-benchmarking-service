package com.itrexgroup.turvo.dbquerybenchmarkingservice.service.metrics;

import org.springframework.util.StopWatch;

import static com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus.SUCCESS;

public class ExecutionTimeMetrics extends PerformanceMetrics {
    private final StopWatch stopWatch;

    public ExecutionTimeMetrics() {
        stopWatch = new StopWatch();
    }

    @Override
    protected void startCollectMetrics() {
        stopWatch.start();
    }

    @Override
    protected MetricsResult finishCollectMetrics() {
        stopWatch.stop();
        long millis = stopWatch.getLastTaskTimeMillis();

        return MetricsResult.builder().
                status(SUCCESS).
                result(Long.toString(millis)).build();
    }
}
