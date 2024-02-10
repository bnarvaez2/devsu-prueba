package com.devsu.account.service;

import com.devsu.account.base.TestBase;
import com.devsu.account.client.customer.CustomerClient;
import com.devsu.account.client.customer.dto.CustomerResponse;
import com.devsu.account.repository.AccountMovementRepository;
import com.devsu.account.repository.AccountRepository;
import com.devsu.account.repository.model.AccountEntity;
import com.devsu.account.repository.model.AccountMovementEntity;
import com.devsu.account.service.exception.ReportInvalidDateException;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.OffsetDateTime;
import java.util.stream.IntStream;

public class ReportServiceTest extends TestBase {
  @Autowired
  private ReportService service;
  @Autowired
  private AccountRepository repository;
  @Autowired
  private AccountMovementRepository movementRepository;
  @MockBean
  private CustomerClient customerClient;

  public static final Long CLIENT_ID = 1234L;
  public static final long ACCOUNT_ID = 100000L;

  @BeforeEach
  void init() {
    repository.deleteAll();
    movementRepository.deleteAll();
  }

  @Test
  public void verifyService() {
    assertNotNull(service);
  }

  @Test
  @DisplayName("generateReport: generate report by clientId and date range")
  public void generateReport() {
    double initialBalance = 20000.0;
    insertAccount(initialBalance);
    insertMovements(initialBalance);
    setupCustomerClient_GetCustomerByIdentification_returnCustomerResponse();

    val report = service.generateReport(CLIENT_ID, "2024-01-01", "2024-01-10");

    Assertions.assertNotNull(report);
    Assertions.assertEquals(CLIENT_ID, report.getIdentification());
    Assertions.assertFalse(report.getAccounts().isEmpty());
    Assertions.assertFalse(report.getAccounts().get(0).getMovements().isEmpty());
  }

  @Test
  @DisplayName("generateReport: given an invalid date, then throw ReportInvalidDateException")
  public void generateReport_throwInvalidDateException() {
    assertThrows(ReportInvalidDateException.class, () ->  {
      service.generateReport(CLIENT_ID, "1800-01-01", "2024-01-10");
    });
  }

  private void insertAccount(Double initialBalance) {
    repository.save(AccountEntity.builder()
      .clientId(CLIENT_ID)
      .type("AHORRO")
      .status(true)
      .initialBalance(initialBalance)
      .currentBalance(initialBalance).build());
  }

  private void insertMovements(Double initialBalance) {
    IntStream.rangeClosed(1, 5).forEach(i -> {
      double amount = i * -10.0;
      movementRepository.save(AccountMovementEntity.builder()
        .accountId(ACCOUNT_ID)
        .movementDate(OffsetDateTime.parse("2024-01-0" + i + "T06:00:12.955313Z"))
        .amount(amount)
        .balance(initialBalance + amount).build());
    });
  }

  private void setupCustomerClient_GetCustomerByIdentification_returnCustomerResponse() {
    Mockito.when(customerClient.getCustomerByIdentification(CLIENT_ID))
      .thenReturn(CustomerResponse.builder().identification(CLIENT_ID).firstName("Brian").lastName("Narvaez").build());
  }
}
