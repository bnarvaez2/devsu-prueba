package com.devsu.account.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountMovementBalanceNotAvailableException extends ResponseStatusException {

  public AccountMovementBalanceNotAvailableException() {
    super(HttpStatus.BAD_REQUEST, "Balance not available");
  }
}
