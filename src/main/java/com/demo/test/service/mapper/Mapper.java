package com.demo.test.service.mapper;

import com.demo.test.dto.UserDto;
import com.demo.test.exceptions.Errors;
import com.demo.test.exceptions.UserExceptions;
import com.demo.test.model.Sex;
import com.demo.test.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * converts Dto to relevant
 * objects and voice-versa
 */
@Service
public class Mapper {

  private final String dateTimeFormat;

  @Autowired
  public Mapper(@Value("${date.input.format}") String dateTimeFormat) {
    this.dateTimeFormat = dateTimeFormat;
  }

  public UserDto toUserDto(User user) {
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

  public User toUser(UserDto userDto) {
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

  public LocalDate getDateOfBirth(String dateOfBirth) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormat);
    try {
      return LocalDate.parse(dateOfBirth, dtf);
    } catch (DateTimeParseException dateTimeParseException) {
      throw new UserExceptions.BadRequestException(dateTimeParseException.getMessage(), Errors.INPUT_DATE_FORMAT);
    }
  }
}
