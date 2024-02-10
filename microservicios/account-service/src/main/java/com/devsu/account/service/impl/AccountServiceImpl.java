package com.devsu.account.service.impl;

import com.devsu.account.client.customer.CustomerClient;
import com.devsu.account.dto.AccountResponse;
import com.devsu.account.dto.NewAccountRequest;
import com.devsu.account.repository.AccountRepository;
import com.devsu.account.service.AccountService;
import com.devsu.account.service.exception.AccountNotFoundException;
import static com.devsu.account.service.mapper.AccountMapper.ACCOUNT_MAPPER;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository repository;
  private final CustomerClient customerClient;

  @Override
  public AccountResponse getAccountById(Long accountId) {
    val account = repository.findByIdAndStatusIsTrue(accountId)
      .orElseThrow(AccountNotFoundException::new);

    return ACCOUNT_MAPPER.toResponse(account);
  }

  @Override
  public List<AccountResponse> getAccountsByClientId(Long clientId) {
    val accounts = repository.findAllByClientIdAndStatusIsTrue(clientId);

    return ACCOUNT_MAPPER.toResponse(accounts);
  }

  @Override
  public List<AccountResponse> getAccounts() {
    val accounts = repository.findAllByStatusIsTrue();

    return ACCOUNT_MAPPER.toResponse(accounts);
  }

  @Override
  public void createAccount(NewAccountRequest request) {
    customerClient.getCustomerByIdentification(request.getClientId());

    repository.save(ACCOUNT_MAPPER.fromRequest(request));
  }

  @Override
  public void deleteAccount(Long accountId) {
    val account = repository.findByIdAndStatusIsTrue(accountId)
      .orElseThrow(AccountNotFoundException::new);

    account.setStatus(false);
    repository.save(account);
  }

  @Override
  public void updateCurrentBalance(Long clientId, Double newBalance) {
    val account = repository.findByIdAndStatusIsTrue(clientId)
      .orElseThrow(AccountNotFoundException::new);

    account.setCurrentBalance(newBalance);
    repository.save(account);
  }

}
