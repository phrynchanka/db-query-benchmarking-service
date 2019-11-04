package com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Metrics report by query name")
public class ReportByQueryName {
    @ApiModelProperty(value = "Query name")
    private String queryName;
    @ApiModelProperty(value = "Query statistics with databases, versions and metrics.")
    private List<QueryStatistics> statistics;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "Query statistics")
    public static class QueryStatistics {
        @ApiModelProperty(value = "Sql")
        private String sql;
        @ApiModelProperty(value = "Metric type")
        private MetricsType metrics;
        @ApiModelProperty(value = "Metrics result")
        private String result;
        @ApiModelProperty(value = "Sql version")
        private String version;
        @ApiModelProperty(value = "Tested database")
        private String database;
        @ApiModelProperty(value = "Query execution status")
        private MetricsExecutionStatus status;
        @ApiModelProperty(value = "Query execution time")
        private Date executed;
    }
}
