package com.itrexgroup.turvo.dbquerybenchmarkingservice.repository;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.model.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepository extends CrudRepository<Query, Long> {
}
