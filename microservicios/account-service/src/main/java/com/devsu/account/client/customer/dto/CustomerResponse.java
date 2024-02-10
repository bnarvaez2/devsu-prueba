package com.devsu.account.client.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse implements Serializable {
  private Long identification;
  private String firstName;
  private String lastName;
  private String gender;
  private Integer age;
  private String address;
  private String phoneNumber;
}
