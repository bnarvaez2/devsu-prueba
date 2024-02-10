package com.devsu.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewMovementRequest implements Serializable {

  @NotNull(message = "clientId can't be null")
  @Min(value = 1, message = "clientId is not valid")
  private Long accountId;

  @NotNull(message = "amount can't be null")
  private Double amount;

  @NotNull(message = "movementDate can't be null")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX", shape = JsonFormat.Shape.STRING)
  private OffsetDateTime movementDate;
}
