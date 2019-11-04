package com.itrexgroup.turvo.dbquerybenchmarkingservice.service.mapper;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.Query;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.QueryPerformance;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.QueryPerformanceHistory;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto.PerformanceQueryDto;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto.ReportByQueryName.QueryStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Mapper
public interface QueryMapper {
    Query mapToQuery(PerformanceQueryDto dto);

    List<QueryPerformanceHistory> mapToHistories(List<QueryPerformance> performances);

    @Mappings({
            @Mapping(source = "result", target = "result"),
            @Mapping(source = "queryPerformance.database", target = "database"),
            @Mapping(source = "queryPerformance.query.sql", target = "sql"),
            @Mapping(source = "queryPerformance.query.version", target = "version"),
            @Mapping(source = "queryPerformance.type", target = "metrics"),
            @Mapping(source = "updated", target = "executed"),
            @Mapping(source = "status", target = "status")
    })
    QueryStatistics mapToQueryStatistics(QueryPerformanceHistory history);

    default List<QueryPerformance> mapToQueryPerformances(PerformanceQueryDto dto) {
        List<QueryPerformance> queryPerformances = new ArrayList<>();
        Query query = mapToQuery(dto);
        dto.getDatabases().forEach(db ->
                dto.getMetrics().stream().
                        map(type -> QueryPerformance.builder().
                                query(query).
                                database(db).
                                type(type).
                                build()).forEach(queryPerformances::add));
        return queryPerformances;
    }

    default QueryPerformanceHistory mapToHistory(QueryPerformance queryPerformance) {
        return QueryPerformanceHistory.builder().
                queryPerformance(queryPerformance).
                status(MetricsExecutionStatus.CREATED).updated(new Date()).build();
    }
}
