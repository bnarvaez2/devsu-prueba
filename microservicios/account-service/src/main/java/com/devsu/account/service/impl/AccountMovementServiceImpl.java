package com.devsu.account.service.impl;

import com.devsu.account.dto.AccountMovementInfo;
import com.devsu.account.dto.AccountMovementResponse;
import com.devsu.account.dto.NewMovementRequest;
import com.devsu.account.repository.AccountMovementRepository;
import com.devsu.account.service.AccountMovementService;
import com.devsu.account.service.AccountService;
import com.devsu.account.service.exception.AccountMovementBalanceNotAvailableException;
import static com.devsu.account.service.mapper.AccountMovementMapper.MOVEMENT_MAPPER;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountMovementServiceImpl implements AccountMovementService {

  private final AccountService accountService;
  private final AccountMovementRepository repository;

  @Override
  public void createMovement(NewMovementRequest request) {
    val account = accountService.getAccountById(request.getAccountId());
    double newBalance = account.getCurrentBalance() + request.getAmount();

    if (newBalance < 0)
      throw new AccountMovementBalanceNotAvailableException();

    repository.save(MOVEMENT_MAPPER.fromRequest(request, newBalance));
    accountService.updateCurrentBalance(account.getId(), newBalance);
  }

  @Override
  public List<AccountMovementResponse> getMovements() {
    val entities = repository.findAll();

    return MOVEMENT_MAPPER.fromEntity(entities);
  }

  @Override
  public List<AccountMovementResponse> getMovementsByAccountId(Long accountId) {
    val entities = repository.findAllByAccountId(accountId);

    return MOVEMENT_MAPPER.fromEntity(entities);
  }

  @Override
  public List<AccountMovementInfo> getMovementBetweenDates(Long accountId, String startDate, String endDate) {
    LocalDate startLocalDate = LocalDate.parse(startDate);
    LocalDate endLocalDate = LocalDate.parse(endDate);

    OffsetDateTime startDateTime = startLocalDate.atStartOfDay().atOffset(ZoneOffset.UTC);
    OffsetDateTime endDateTime = endLocalDate.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC);

    val entities = repository.findByAccountIdAndMovementDateBetweenOrderByMovementDateDesc(accountId, startDateTime, endDateTime);

    return MOVEMENT_MAPPER.fromEntities(entities);
  }

}
