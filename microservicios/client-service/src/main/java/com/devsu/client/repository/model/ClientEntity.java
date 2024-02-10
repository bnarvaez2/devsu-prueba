package com.devsu.client.repository.model;

import com.devsu.client.repository.base.PersonBase;
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
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class ClientEntity extends PersonBase {

  @Id
  @GeneratedValue
  private UUID id;
  private String password;
  private boolean status;

  @CreationTimestamp
  private OffsetDateTime creationDate;

  @Builder.Default
  private String creationUser = "app-client";

  @UpdateTimestamp
  private OffsetDateTime updateDate;

  @Builder.Default
  private String updateUser = "app-client";
}
