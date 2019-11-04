package com.itrexgroup.turvo.dbquerybenchmarkingservice.service.metrics;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.constants.MetricsExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsResult {
    private String result;
    private MetricsExecutionStatus status;
}
