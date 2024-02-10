package com.devsu.account.service.impl;

import com.devsu.account.client.customer.CustomerClient;
import static com.devsu.account.commom.util.DateUtil.validateDate;
import com.devsu.account.dto.ReportResponse;
import com.devsu.account.service.AccountMovementService;
import com.devsu.account.service.AccountService;
import com.devsu.account.service.ReportService;
import com.devsu.account.service.exception.ReportInvalidDateException;
import static com.devsu.account.service.mapper.ReportMapper.REPORT_MAPPER;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final CustomerClient customerClient;
  private final AccountService accountService;
  private final AccountMovementService movementService;

  @Override
  public ReportResponse generateReport(Long clientId, String startDate, String endDate) {
    if (validateDate(startDate) || validateDate(endDate)) throw new ReportInvalidDateException();

    val client = customerClient.getCustomerByIdentification(clientId);
    val accounts = accountService.getAccountsByClientId(clientId);

    val accountInfoList = accounts.stream().map(account -> {
      val movements = movementService.getMovementBetweenDates(account.getId(), startDate, endDate);
      return REPORT_MAPPER.toAccountInfo(account, movements);
    }).collect(Collectors.toList());

    return REPORT_MAPPER.toReportResponse(client, accountInfoList);
  }

}
