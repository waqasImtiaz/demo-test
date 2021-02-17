package com.demo.test.dto;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * a data transfer object for User type objects
 * it has all the constraints on its values
 * which are coming from out side
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UserDto {
  @NotBlank(message = "validation.constraints.first-name.NotBlank")
  @Pattern(regexp = "[A-Za-z ]+", message = "Invalid first name. Only characters are acceptable")
  @Size(max = 30, message = "First Name cannot be more than 30 characters")
  private String firstName;

  @NotBlank(message = "validation.constraints.last-name.NotBlank")
  @Pattern(regexp = "[A-Za-z ]+", message = "Invalid last name. Only characters are acceptable")
  @Size(max = 30, message = "Last Name cannot be more than 30 characters")
  private String lastName;

  @NotBlank(message = "validation.constraints.date-of-birth.NotBlank")
  @Pattern(regexp = "([0-9]{2})-([0-9]{2})-([0-9]{4})", message = "Invalid date of birth. Allowed pattern is dd-MM-yyyy")
  private String dateOfBirth;

  @NotBlank(message = "validation.constraints.email.NotBlank")
  @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email address.")
  @Size(max = 100, message = "Email cannot be more than 100 characters")
  private String email;

  @NotBlank(message = "validation.constraints.password.NotBlank")
  @Size(max = 100, message = "Password Name cannot be more than 100 characters")
  private String password;

  @NotBlank(message = "validation.constraints.sex.NotBlank")
  @Pattern(regexp = "(?i)(male|female)", message = "Invalid first name. Only characters are acceptable")
  private String sex;

  @NotBlank(message = "validation.constraints.country.NotBlank")
  @Pattern(regexp = "(?i)france", message = "Invalid country. Only french residence can register")
  private String country;

  @Pattern(regexp = "|^\\d{10}$", message = "Invalid phone number. Only numbers at maximum 10 digits are allowed")
  @Size(max = 20, message = "Last Name cannot be more than 20 digits")
  private String phoneNumber;

  @Override
  public String toString() {
    return "UserDto{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", dateOfBirth='" + dateOfBirth + '\'' +
        ", email='" + email + '\'' +
        ", password='" + (StringUtils.isNotBlank(password) ? "******" : password) + '\'' +
        ", sex='" + sex + '\'' +
        ", country='" + country + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
