package com.itrexgroup.turvo.dbquerybenchmarkingservice.service;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto.PerformanceQueryDto;

import java.util.List;

public interface QueryPersistenceService {
    void persistQueryRequest(List<PerformanceQueryDto> performanceQueryDto);
}
