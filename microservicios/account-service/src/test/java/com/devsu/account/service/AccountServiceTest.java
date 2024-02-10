package com.devsu.account.service;

import com.devsu.account.base.TestBase;
import com.devsu.account.client.customer.CustomerClient;
import com.devsu.account.client.customer.dto.CustomerResponse;
import com.devsu.account.dto.NewAccountRequest;
import com.devsu.account.repository.AccountRepository;
import com.devsu.account.repository.model.AccountEntity;
import com.devsu.account.service.exception.AccountNotFoundException;
import static java.nio.charset.StandardCharsets.UTF_8;
import lombok.val;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.http.HttpStatusCode.valueOf;
import org.springframework.web.client.HttpClientErrorException;

public class AccountServiceTest extends TestBase {

  @Autowired
  private AccountService service;
  @Autowired
  private AccountRepository repository;
  @MockBean
  private CustomerClient customerClient;

  @BeforeEach
  void init() {
    repository.deleteAll();
  }

  @Test
  public void verifyService() {
    assertNotNull(service);
  }

  @Test
  @DisplayName("getAccountById: given an accountId then return account")
  public void getAccountById() {
    insertAccount(true);
    val account = service.getAccountById(100000L);

    assertNotNull(account);
  }

  @Test
  @DisplayName("getAccountById: given a non existing accountId then throw NotFoundException")
  public void getAccountById_throwNotFoundException() {
    assertThrows(AccountNotFoundException.class, () -> {
      service.getAccountById(100002L);
    });
  }

  @Test
  @DisplayName("getAccountById: given an account deleted then throw NotFoundException")
  public void getAccountById_throwNotFoundException_secondCase() {
    insertAccount(false);

    assertThrows(AccountNotFoundException.class, () -> {
      service.getAccountById(100000L);
    });
  }

  @Test
  @DisplayName("getAccountByClientId: given a clientId then return their accounts")
  public void getAccountByClientId() {
    insertAccount(true);

    val accounts = service.getAccountsByClientId(1234L);

    assertFalse(accounts.isEmpty());
  }

  @Test
  @DisplayName("getAccountByClientId: given a clientId without accounts then return empty list")
  public void getAccountByClientId_secondCase() {
    val accounts = service.getAccountsByClientId(1234L);

    assertTrue(accounts.isEmpty());
  }

  @Test
  @DisplayName("getAccountByClientId: given a clientId return their active accounts")
  public void getAccountByClientId_thirdCase() {
    insertAccount(false);
    insertAccount(true);
    val accounts = service.getAccountsByClientId(1234L);

    assertEquals(1, accounts.size());
  }

  @Test
  @DisplayName("getAccounts: return all active accounts")
  public void getAccounts() {
    insertAccount(true);
    val accounts = service.getAccounts();

    assertFalse(accounts.isEmpty());
  }

  @Test
  @DisplayName("getAccounts: return empty account list")
  public void getAccounts_secondCase() {
    insertAccount(false);

    val accounts = service.getAccounts();

    assertTrue(accounts.isEmpty());
  }

  @Test
  @DisplayName("createAccount: insert an account and check if it was saved in database")
  public void createAccount() {
    Long clientId = 1124L;
    val request = NewAccountRequest.builder().type("AHORRO").initialBalance(10.0).clientId(clientId).build();
    setupCustomerClient_GetCustomerByIdentification_returnCustomerResponse(clientId);

    service.createAccount(request);

    assertEquals(1, repository.findAllByClientIdAndStatusIsTrue(clientId).size());
  }

  @Test
  @DisplayName("createAccount: given a new account with a non existing clientId then throw Exception")
  public void createAccount_throwClientException() {
    Long clientId = 1124L;
    val request = NewAccountRequest.builder().type("AHORRO").initialBalance(10.0).clientId(clientId).build();
    setupCustomerClient_GetCustomerByIdentification_throwNotFoundException(clientId);

    assertThrows(Exception.class, () -> {
      service.createAccount(request);
    }, "Client not found");
  }

  @Test
  @DisplayName("deleteClient: delete a client and check if its status changed")
  public void deleteClient() {
    ;
    insertAccount(true);

    assertTrue(repository.findByIdAndStatusIsTrue(100000L).isPresent());

    service.deleteAccount(100000L);

    assertFalse(repository.findByIdAndStatusIsTrue(100000L).isPresent());
  }

  @Test
  @DisplayName("deleteAccount: given a non existing account then throw a NotFoundException")
  public void deleteAccount_throwNotFoundException() {
    assertThrows(AccountNotFoundException.class, () -> {
      service.deleteAccount(100000L);
    });
  }

  @Test
  @DisplayName("deleteAccount: given an account deleted then throw a NotFoundException")
  public void deleteAccount_throwNotFoundException_secondCase() {
    insertAccount(false);

    assertThrows(AccountNotFoundException.class, () -> {
      service.deleteAccount(100000L);
    });
  }

  @Test
  @DisplayName("updateCurrentBalance: update the balance of an account, then check if it was modified in the database")
  public void updateCurrentBalance() {
    insertAccount(true);

    service.updateCurrentBalance(100000L, 2000.0);

    val account = repository.findByIdAndStatusIsTrue(100000L);
    assertTrue(account.isPresent());
    assertEquals(2000.0, account.get().getCurrentBalance());
  }

  @Test
  @DisplayName("updateCurrentBalance: update the balance of an account deleted, then throw NotFoundException")
  public void updateCurrentBalance_throwNotFoundException() {
    insertAccount(false);

    assertThrows(AccountNotFoundException.class, () -> {
      service.updateCurrentBalance(100000L, 2000.0);
    });
  }

  @Test
  @DisplayName("updateCurrentBalance: update the balance of a non existing account, then throw NotFoundException")
  public void updateCurrentBalance_throwNotFoundException_secondCase() {
    assertThrows(AccountNotFoundException.class, () -> {
      service.updateCurrentBalance(100000L, 2000.0);
    });
  }

  private void insertAccount(boolean status) {
    repository.save(AccountEntity.builder().clientId(1234L).status(status).build());
  }

  private void setupCustomerClient_GetCustomerByIdentification_returnCustomerResponse(Long clientId) {
    Mockito.when(customerClient.getCustomerByIdentification(clientId))
      .thenReturn(CustomerResponse.builder().identification(clientId).firstName("Brian").lastName("Narvaez").build());
  }

  private void setupCustomerClient_GetCustomerByIdentification_throwNotFoundException(Long clientId) {
    Mockito.when(customerClient.getCustomerByIdentification(clientId))
      .thenThrow(new HttpClientErrorException(valueOf(404), "Not Found", "Client not found".getBytes(), UTF_8));
  }
}
