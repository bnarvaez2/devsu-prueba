package com.devsu.account.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_movement")
public class AccountMovementEntity {

  @Id
  @GeneratedValue
  private UUID id;
  private Long accountId;
  private Double amount;
  private OffsetDateTime movementDate;
  private Double balance;

  @CreationTimestamp
  private OffsetDateTime creationDate;

  @Builder.Default
  private String creationUser = "app-account";

  @UpdateTimestamp
  private OffsetDateTime updateDate;

  @Builder.Default
  private String updateUser = "app-account";
}
