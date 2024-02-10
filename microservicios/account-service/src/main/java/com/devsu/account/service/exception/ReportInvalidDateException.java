package com.devsu.account.service.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.server.ResponseStatusException;

public class ReportInvalidDateException extends ResponseStatusException {
  public ReportInvalidDateException() {
    super(NOT_FOUND, "Date no valid for query remember format is 'yyyy-mm-dd'");
  }
}
