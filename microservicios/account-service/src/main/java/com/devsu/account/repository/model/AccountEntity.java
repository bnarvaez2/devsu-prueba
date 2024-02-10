package com.devsu.account.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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

@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
  @SequenceGenerator(name = "account_seq", sequenceName = "account_seq", initialValue = 100000, allocationSize = 1)
  private Long id;
  private String type;
  private Double initialBalance;
  private boolean status;
  private Double currentBalance;
  private Long clientId;

  @CreationTimestamp
  private OffsetDateTime creationDate;

  @Builder.Default
  private String creationUser = "app-account";

  @UpdateTimestamp
  private OffsetDateTime updateDate;

  @Builder.Default
  private String updateUser = "app-account";
}