package com.devsu.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequest {
  private String firstName;
  private String lastName;
  private String gender;
  private Integer age;
  private String address;
  private String phoneNumber;
  private String password;
}
