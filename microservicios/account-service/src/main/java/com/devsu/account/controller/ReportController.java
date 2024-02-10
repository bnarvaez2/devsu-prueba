package com.devsu.account.controller;

import com.devsu.account.dto.ReportResponse;
import com.devsu.account.service.ReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReportController {

  private final ReportService service;

  @GetMapping
  public ReportResponse generateReport(
    @Valid
    @Pattern (regexp = "^(\\d{4}-\\d{2}-\\d{2},)+\\d{4}-\\d{2}-\\d{2}$",
      message = "El rango de fechas debe contener exactamente dos fechas separadas por una coma y en formato yyyy-mm-dd")
    @RequestParam("fecha") String dateRange,
    @RequestParam("clientId") Long clientId) {
    String[] dates = dateRange.split(",");
    String startDate = dates[0];
    String endDate = dates[1];

    return service.generateReport(clientId, startDate, endDate);
  }


}
