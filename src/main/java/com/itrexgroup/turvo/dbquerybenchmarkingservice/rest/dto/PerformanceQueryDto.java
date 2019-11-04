package com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@ApiModel(description = "Details about query performance measure request.")
public class PerformanceQueryDto {
    @ApiModelProperty(notes = "Metric to measure", example = "[EXECUTION_TIME]")
    @NotEmpty
    private List<MetricsType> metrics;

    @ApiModelProperty(notes = "Query name", example = "select all posts")
    @NotEmpty
    private String queryName;

    @ApiModelProperty(notes = "Sql to test")
    @NotEmpty
    private String sql;

    @ApiModelProperty(notes = "Sql version")
    @NotEmpty
    private String version;

    @ApiModelProperty(notes = "List of database names to run sql")
    @NotEmpty
    private List<String> databases;
}
