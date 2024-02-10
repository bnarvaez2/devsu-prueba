package com.devsu.client.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewClientRequest {

  @NotNull(message = "password is missing")
  @NotEmpty(message = "password can't be empty")
  private String password;

  @NotNull(message = "identification is missing")
  @Min(value = 0, message = "identification is not valid")
  private Long identification;

  @NotNull(message = "firstName is missing")
  @NotEmpty(message = "firstName can't be empty")
  private String firstName;

  @NotNull(message = "lastName is missing")
  @NotEmpty(message = "lastName can't be empty")
  private String lastName;
  private String gender;
  private Integer age;
  private String address;
  private String phoneNumber;
  @Builder.Default
  private boolean status = true;

}
