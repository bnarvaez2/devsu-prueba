package com.devsu.client.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientAlreadyExistException extends ResponseStatusException {
  public ClientAlreadyExistException() {
    super(HttpStatus.CONFLICT, "The client identification is already exist");
  }
}
