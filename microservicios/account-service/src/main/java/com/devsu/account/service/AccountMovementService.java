package com.devsu.account.service;

import com.devsu.account.dto.AccountMovementInfo;
import com.devsu.account.dto.AccountMovementResponse;
import com.devsu.account.dto.NewMovementRequest;
import java.util.List;

public interface AccountMovementService {

  void createMovement(NewMovementRequest request);

  List<AccountMovementResponse> getMovements();

  List<AccountMovementResponse> getMovementsByAccountId(Long accountId);

  List<AccountMovementInfo> getMovementBetweenDates(Long accountId, String startDate, String endDate);
}
