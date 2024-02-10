package com.devsu.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo implements Serializable {

  private Long accountId;
  private String type;
  private boolean status;
  private Double initialBalance;
  private Double currentBalance;
  List<AccountMovementInfo> movements;
}
