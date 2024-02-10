package com.devsu.account.service;

import com.devsu.account.base.TestBase;
import com.devsu.account.dto.NewMovementRequest;
import com.devsu.account.repository.AccountMovementRepository;
import com.devsu.account.repository.AccountRepository;
import com.devsu.account.repository.model.AccountEntity;
import com.devsu.account.repository.model.AccountMovementEntity;
import com.devsu.account.service.exception.AccountMovementBalanceNotAvailableException;
import com.devsu.account.service.exception.AccountNotFoundException;
import lombok.val;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.OffsetDateTime;

public class AccountMovementServiceTest extends TestBase {

  @Autowired
  private AccountMovementService movementService;
  @Autowired
  private AccountMovementRepository movementRepository;
  @Autowired
  private AccountRepository accountRepository;

  @BeforeEach
  void init() {
    movementRepository.deleteAll();
    accountRepository.deleteAll();
  }

  @Test
  public void verifyService() {
    assertNotNull(movementService);
  }

  @Test
  @DisplayName("createMovement: create movement by depositing an amount, then check changes in balance")
  public void createMovement_depositCase() {
    insertAccount();
    Long accountId = 100000L;
    val request = NewMovementRequest.builder().accountId(accountId).movementDate(OffsetDateTime.now()).amount(10.0).build();
    val oldAccount = accountRepository.findByIdAndStatusIsTrue(accountId).get();

    movementService.createMovement(request);

    val currentAccount = accountRepository.findByIdAndStatusIsTrue(accountId).get();

    assertFalse(movementRepository.findAllByAccountId(request.getAccountId()).isEmpty());
    assertNotEquals(oldAccount.getCurrentBalance(), currentAccount.getCurrentBalance());
  }

  @Test
  @DisplayName("createMovement: create movement by withdrawing an amount, then check the changes in the balance")
  public void createMovement_withdrawalCase() {
    insertAccount();
    Long accountId = 100000L;
    val request = NewMovementRequest.builder().accountId(accountId).movementDate(OffsetDateTime.now()).amount(-10.0).build();
    val oldAccount = accountRepository.findByIdAndStatusIsTrue(accountId).get();

    movementService.createMovement(request);

    val currentAccount = accountRepository.findByIdAndStatusIsTrue(accountId).get();

    assertFalse(movementRepository.findAllByAccountId(request.getAccountId()).isEmpty());
    assertNotEquals(oldAccount.getCurrentBalance(), currentAccount.getCurrentBalance());
  }

  @Test
  @DisplayName("createMovement: given a movement in a non existing account, then throw AccountNotFoundException")
  public void createMovement_throwAccountNotFoundException() {
    Long accountId = 100000L;
    val request = NewMovementRequest.builder().accountId(accountId).movementDate(OffsetDateTime.now()).amount(10.0).build();

    assertThrows(AccountNotFoundException.class, () -> {
      movementService.createMovement(request);
    });
  }

  @Test
  @DisplayName("createMovement: given a withdrawal in an account with insufficient balance, " +
    "then throw AccountMovementBalanceNotAvailableException")
  public void createMovement_throwAccountMovementBalanceNotAvailableException() {
    insertAccount();
    Long accountId = 100000L;
    val request = NewMovementRequest.builder().accountId(accountId).movementDate(OffsetDateTime.now()).amount(-999.0).build();

    assertThrows(AccountMovementBalanceNotAvailableException.class, () -> {
      movementService.createMovement(request);
    });
  }

  @Test
  @DisplayName("getMovements: return all movements")
  public void getMovements() {
    insertMovements();
    val accounts = movementService.getMovements();

    assertFalse(accounts.isEmpty());
  }

  @Test
  @DisplayName("getAccounts: return empty list")
  public void getMovements_secondCase() {
    val accounts = movementService.getMovements();

    assertTrue(accounts.isEmpty());
  }

  private void insertMovements() {
    movementRepository.save(AccountMovementEntity.builder()
      .accountId(1234L)
      .movementDate(OffsetDateTime.now())
      .amount(10.0)
      .balance(10.0).build());
  }

  private void insertAccount() {
    accountRepository.save(AccountEntity.builder().clientId(1234L).status(true).currentBalance(100.0).build());
  }
}
