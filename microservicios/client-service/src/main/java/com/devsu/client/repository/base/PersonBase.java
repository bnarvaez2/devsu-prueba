package com.devsu.client.repository.base;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@Setter
@Getter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PersonBase implements Serializable {

  @Column(name = "identification")
  protected Long identification;

  @Column(name = "last_name")
  protected String lastName;

  @Column(name = "first_name")
  protected String firstName;

  @Column(name = "gender")
  protected String gender;

  @Column(name = "age")
  protected Integer age;

  @Column(name = "address")
  protected String address;

  @Column(name = "phone_number")
  protected String phoneNumber;
}
