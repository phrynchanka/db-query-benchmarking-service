package com.itrexgroup.turvo.dbquerybenchmarkingservice.repository;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.QueryPerformance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryPerformanceRepository extends CrudRepository<QueryPerformance, Long> {
}
