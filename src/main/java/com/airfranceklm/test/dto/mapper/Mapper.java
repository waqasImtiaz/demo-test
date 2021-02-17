package com.airfranceklm.test.dto.mapper;

import com.airfranceklm.test.dto.UserDto;
import com.airfranceklm.test.model.Sex;
import com.airfranceklm.test.model.User;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * converts Dto to relevant
 * objects and voice-versa
 */
public class Mapper {

  private Mapper() {}

  public static UserDto toUserDto(User user) {
    return UserDto.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .dateOfBirth(user.getDateOfBirth().toString())
        .email(user.getEmail())
        .sex(user.getSex().toString())
        .country(user.getCountry())
        .password(StringUtils.isNotBlank(user.getPassword()) ? "*****" : user.getPassword())
        .phoneNumber(user.getPhoneNumber())
        .build();
  }

  public static User toUser(UserDto userDto) {
    return User.builder()
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .dateOfBirth(getDateOfBirth(userDto.getDateOfBirth()))
        .email(userDto.getEmail())
        .password(userDto.getPassword())
        .sex(Sex.valueOf(userDto.getSex().toUpperCase()))
        .phoneNumber(userDto.getPhoneNumber())
        .country(userDto.getCountry())
        .build();
  }

  private static LocalDate getDateOfBirth(String dateOfBirth) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(dateOfBirth, dtf);
  }
}
