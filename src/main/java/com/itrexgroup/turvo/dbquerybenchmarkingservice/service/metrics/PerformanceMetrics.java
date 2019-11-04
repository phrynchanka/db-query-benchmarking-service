package com.itrexgroup.turvo.dbquerybenchmarkingservice.service.metrics;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.remote.RemoteDatabase;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.remote.RemoteDatabaseInvocationException;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsType;


public abstract class PerformanceMetrics {
    public MetricsResult collectMetrics(RemoteDatabase remoteDatabase, String sql) throws RemoteDatabaseInvocationException {
        startCollectMetrics();
        remoteDatabase.execute(sql);
        return finishCollectMetrics();
    }

    protected abstract void startCollectMetrics();

    protected abstract MetricsResult finishCollectMetrics();

    public static PerformanceMetrics createMetrics(MetricsType type) {
        switch (type) {
            case EXECUTION_TIME:
            default:
                return new ExecutionTimeMetrics();
        }
    }
}
