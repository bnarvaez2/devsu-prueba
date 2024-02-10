package com.devsu.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse implements Serializable {

  private Long id;
  private String type;
  private Double initialBalance;
  private boolean status;
  private Double currentBalance;
  private Long clientId;
}
