package com.airfranceklm.test.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
  @Size(max = 30, message = "Must be less than 30")
  private String firstName;

  @Column(name = "last_name", nullable = false)
  @Size(max = 30, message = "Must be less than 30")
  private String lastName;

  @Column(name = "date_of_birth", nullable = false)
  private LocalDate dateOfBirth;

  @Column(name = "email", nullable = false, unique = true)
  @Size(max = 100, message = "Must be less than 100")
  private String email;

  @Column(name = "password", nullable = false)
  @Size(max = 100, message = "Must be less than 100")
  private String password;

  @Column(name = "sex")
  private Sex sex;

  @Column(name = "country", nullable = false)
  @Size(max = 30, message = "Must be less than 30")
  private String country;

  @Column(name = "phone_number")
  @Size(max = 20, message = "Must be less than 20")
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
