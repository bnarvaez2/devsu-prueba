package com.devsu.account.service;

import com.devsu.account.dto.AccountResponse;
import com.devsu.account.dto.NewAccountRequest;
import java.util.List;

public interface AccountService {

  AccountResponse getAccountById(Long accountId);

  List<AccountResponse> getAccountsByClientId(Long clientId);

  List<AccountResponse> getAccounts();

  void createAccount(NewAccountRequest request);

  void deleteAccount(Long accountId);

  void updateCurrentBalance(Long accountId, Double newBalance);
}
