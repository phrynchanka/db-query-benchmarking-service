package com.itrexgroup.turvo.dbquerybenchmarkingservice.service;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.repository.QueryPerformanceHistoryRepository;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto.ReportByQueryName;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.service.mapper.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ReportingServiceImpl implements ReportingService {
    @Autowired
    private QueryPerformanceHistoryRepository historyRepository;

    @Autowired
    private QueryMapper queryMapper;

    @Override
    public ReportByQueryName generateReportByQueryName(String queryName) {
        return ReportByQueryName.builder().
                queryName(queryName).
                statistics(historyRepository.
                        findAllByQueryPerformance_Query_QueryName(queryName).
                        stream().
                        map(queryMapper::mapToQueryStatistics).
                        collect(Collectors.toList())).
                build();
    }
}
