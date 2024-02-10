package com.devsu.client.service.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.server.ResponseStatusException;

public class ClientNotFoundException extends ResponseStatusException {
  public ClientNotFoundException() {
    super(NOT_FOUND, "Client not found");
  }
}
