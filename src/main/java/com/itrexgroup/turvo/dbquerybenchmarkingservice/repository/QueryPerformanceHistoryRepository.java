package com.itrexgroup.turvo.dbquerybenchmarkingservice.repository;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.QueryPerformanceHistory;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryPerformanceHistoryRepository extends CrudRepository<QueryPerformanceHistory, Long> {
    List<QueryPerformanceHistory> findTop100ByStatus(MetricsExecutionStatus status);

    List<QueryPerformanceHistory> findAllByQueryPerformance_Query_QueryNameAndStatus(String queryName, MetricsExecutionStatus status);

    List<QueryPerformanceHistory> findAllByQueryPerformance_Query_QueryName(String queryName);
}
