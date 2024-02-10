package com.devsu.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountMovementInfo implements Serializable {

  private Double amount;
  private Double balance;
  private OffsetDateTime movementDate;
}
