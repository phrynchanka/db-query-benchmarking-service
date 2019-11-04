package com.itrexgroup.turvo.dbquerybenchmarkingservice.service;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.remote.RemoteDatabaseConfigMap;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.repository.QueryPerformanceHistoryRepository;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.repository.QueryPerformanceRepository;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto.PerformanceQueryDto;
import com.itrexgroup.turvo.dbquerybenchmarkingservice.service.mapper.QueryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class QueryPersistenceServiceImpl implements QueryPersistenceService {
    @Autowired
    private QueryPerformanceHistoryRepository historyRepository;

    @Autowired
    private QueryPerformanceRepository performanceRepository;

    @Autowired
    private RemoteDatabaseConfigMap databases;

    @Autowired
    private QueryMapper queryMapper;

    @Override
    @Transactional
    public void persistQueryRequest(List<PerformanceQueryDto> queryDtos) {
        Set<String> databases = this.databases.getRemotes().keySet();
        queryDtos.stream().
                map(queryMapper::mapToQueryPerformances).
                flatMap(List::stream).
                filter(q -> databases.contains(q.getDatabase())).
                map(performanceRepository::save).
                map(queryMapper::mapToHistory).
                forEach(historyRepository::save);

        log.info("Performance Query Request persisted.");
    }
}
