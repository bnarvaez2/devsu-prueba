package com.devsu.account.controller;

import static com.devsu.account.commom.util.ResponseUtil.response;
import com.devsu.account.dto.AccountMovementResponse;
import com.devsu.account.dto.GenericResponse;
import com.devsu.account.dto.NewMovementRequest;
import com.devsu.account.service.AccountMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class AccountMovementController {

  private final AccountMovementService service;

  @PostMapping
  public ResponseEntity<GenericResponse> createMovement(@Valid @RequestBody NewMovementRequest request) {
    service.createMovement(request);

    return response("Account movement was crated.", CREATED);
  }

  @GetMapping
  public List<AccountMovementResponse> getMovements() {
    return service.getMovements();
  }

  @GetMapping("/cuenta/{id}")
  public List<AccountMovementResponse> getMovements(@PathVariable(value = "id") Long clientId) {
    return service.getMovementsByAccountId(clientId);
  }

}
