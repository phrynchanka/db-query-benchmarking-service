package com.itrexgroup.turvo.dbquerybenchmarkingservice.service;

import com.itrexgroup.turvo.dbquerybenchmarkingservice.rest.dto.ReportByQueryName;

public interface ReportingService {
    ReportByQueryName generateReportByQueryName(String queryName);
}
