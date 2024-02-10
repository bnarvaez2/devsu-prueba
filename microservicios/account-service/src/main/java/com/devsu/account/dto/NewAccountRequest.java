package com.devsu.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAccountRequest implements Serializable {

  @NotNull(message = "clientId can't be null")
  @Min(value = 1, message = "clientId is not valid")
  private Long clientId;

  @NotNull(message = "type can't be null")
  @Pattern(regexp = "(?i)(AHORRO|CORRIENTE)", message = "type is not valid")
  private String type;

  @NotNull(message = "initialBalance can't be null")
  @Min(value = 100, message = "initialBalance must be equal to or greater than 100.")
  private Double initialBalance;

  @Builder.Default
  private boolean status = true;

}
