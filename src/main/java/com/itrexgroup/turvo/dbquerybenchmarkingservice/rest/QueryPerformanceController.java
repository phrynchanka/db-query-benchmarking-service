package com.itrexgroup.turvo.dbquerybenchmarkingservice.rest;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto.PerformanceQueryDto;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto.ReportByQueryName;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.service.QueryPersistenceService;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.service.ReportingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "Query Performance Measure API")
@RestController
@Validated
public class QueryPerformanceController {
    @Autowired
    private QueryPersistenceService queryPersistenceService;

    @Autowired
    private ReportingService reportingService;

    @ApiOperation(value = "Measure query performance.", response = ResponseEntity.class)
    @PostMapping("measure-performance")
    public ResponseEntity executeQueryTimeProcessing(@Valid @RequestBody List<PerformanceQueryDto> performanceQueryDtos) {
        queryPersistenceService.persistQueryRequest(performanceQueryDtos);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Reports by query metrics execution.", response = ReportByQueryName.class)
    @GetMapping("report/{queryName}")
    public ReportByQueryName getReport(@NotEmpty @PathVariable String queryName) {
        return reportingService.generateReportByQueryName(queryName);
    }
}
