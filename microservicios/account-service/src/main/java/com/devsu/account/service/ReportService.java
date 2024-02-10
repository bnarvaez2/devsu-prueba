package com.devsu.account.service;

import com.devsu.account.dto.ReportResponse;

public interface ReportService {

  ReportResponse generateReport(Long clientId, String startDate, String endDate);
}
