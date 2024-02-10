package com.devsu.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountMovementResponse {
  private UUID id;
  private Long accountId;
  private Double amount;
  private OffsetDateTime movementDate;
  private Double balance;
}
