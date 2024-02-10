package com.devsu.account.controller;

import static com.devsu.account.commom.util.ResponseUtil.response;
import com.devsu.account.dto.AccountResponse;
import com.devsu.account.dto.GenericResponse;
import com.devsu.account.dto.NewAccountRequest;
import com.devsu.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService service;

  @PostMapping
  public ResponseEntity<GenericResponse> createAccount(@Valid @RequestBody NewAccountRequest request) {
    service.createAccount(request);

    return response("Account was created.", HttpStatus.CREATED);
  }

  @GetMapping
  public List<AccountResponse> getAccounts() {
    return service.getAccounts();
  }

  @GetMapping("/{accountId}")
  public AccountResponse getAccountById(@PathVariable(value = "accountId") Long accountId) {
    return service.getAccountById(accountId);
  }

  @GetMapping("/client")
  public List<AccountResponse> getAccountsByClientId(@RequestParam(value = "identification") Long clientId) {
    return service.getAccountsByClientId(clientId);
  }

  @DeleteMapping
  public ResponseEntity<GenericResponse> deleteAccount(@RequestParam Long accountId) {
    service.deleteAccount(accountId);

    return response("Account was deleted.", HttpStatus.OK);
  }
}
