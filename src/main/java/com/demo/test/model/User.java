package com.demo.test.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


/**
 * entity used for ORM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "date_of_birth", nullable = false)
  private LocalDate dateOfBirth;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "sex", nullable = false)
  private Sex sex;

  @Column(name = "country", nullable = false)
  private String country;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "enabled", nullable = false)
  private boolean enabled = false;

  @Column(name = "locked", nullable = false)
  private boolean locked = false;

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        ", email='" + email + '\'' +
        ", password='" + "******" + '\'' +
        ", sex=" + sex +
        ", country='" + country + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", enabled=" + enabled +
        ", locked=" + locked +
        '}';
  }
}
