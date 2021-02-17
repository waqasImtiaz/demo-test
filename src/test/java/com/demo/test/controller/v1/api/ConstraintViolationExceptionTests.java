package com.demo.test.controller.v1.api;

import com.demo.test.dto.ApiSuccessDto;
import com.demo.test.dto.UserDto;
import com.demo.test.model.User;
import com.demo.test.service.mapper.Mapper;
import com.demo.test.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConstraintViolationExceptionTests {

  @Autowired
  private UserController userController;

  @Autowired
  private Mapper mapper;

  @Autowired
  @MockBean
  private UserService userService;

  @Test
  void givenFirstName_whenEmpty_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("validation.constraints.first-name.NotBlank"));
  }

  @Test
  void givenFirstName_whenInvalid_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("aasd1").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("Invalid first name. Only characters are acceptable"));
  }

  @Test
  void givenLastName_whenEmpty_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("imtiaz").lastName("").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("validation.constraints.last-name.NotBlank"));
  }
  @Test

  void givenLastName_whenInvalid_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("imtiaz").lastName("asf.12").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("Invalid last name. Only characters are acceptable"));
  }

  @Test
  void givenDateOfBirth_whenEmpty_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("validation.constraints.date-of-birth.NotBlank"));
  }

  @Test
  void givenDateOfBirth_whenInvalid_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10/10/1990")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("Invalid date of birth. Allowed pattern is dd-MM-yyyy"));
  }

  @Test
  void givenPassword_whenEmpty_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("validation.constraints.password.NotBlank"));
  }

  @Test
  void givenEmail_whenEmpty_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("validation.constraints.email.NotBlank"));
  }

  @Test
  void givenEmail_whenInvalid_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("Invalid email address."));
  }

  @Test
  void givenGender_whenEmpty_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("validation.constraints.sex.NotBlank"));
  }

  @Test
  void givenGender_whenInvalid_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("malee").country("france").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("Invalid first name. Only characters are acceptable"));
  }

  @Test
  void givenCountry_whenEmpty_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("validation.constraints.country.NotBlank"));
  }

  @Test
  void givenCountry_whenNotFrance_thenThrowConstraintViolationExceptionTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("england").phoneNumber("0981797848")
        .build();

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("Invalid country. Only french residence can register"));
  }

  @Test
  void givenPhoneNumber_whenEmpty_thenReturnUserTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("")
        .build();
    User waqas = mapper.toUser(waqasDto);

    Mockito.when(userService.toUser(waqasDto)).thenReturn(waqas);
    Mockito.when(userService.registerUser(waqas)).thenReturn(waqas);
    ResponseEntity<Object> expectedResult = getResponseEntityWithCreateAndBody(waqas);
    ResponseEntity<Object> actualResult = userController.registerUser(waqasDto, "v1");

    Assertions.assertEquals((ApiSuccessDto)expectedResult.getBody(), (ApiSuccessDto)actualResult.getBody());
  }

  @Test
  void givenPhoneNumber_whenInvalid_thenReturnUserTest() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("07123456789")
        .build();
    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userController
        .registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("Invalid phone number. Only numbers at maximum 10 digits are allowed"));
  }

  @Test
  void givenUserId_whenInvalid_thenThrowConstrantViolationExceptionTest() {
    User waqas = getWaqas();
    waqas.setId(-1);

    ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,() ->
        userController.getUser(waqas.getId()));

    assertTrue(exception.getMessage().contains("id cannot be negative"));
  }

  private ResponseEntity<Object> getResponseEntityWithCreateAndBody(User waqas) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    ApiSuccessDto apiSuccessDto = ApiSuccessDto.builder().object(mapper.toUserDto(waqas))
        .timeStamp(LocalDateTime.now().format(dateTimeFormatter)).status(HttpStatus.CREATED)
        .resourceUri("/v1/users/" + waqas.getId()).build();
    return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessDto);
  }

  private User getWaqas() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();
    return mapper.toUser(waqasDto);
  }
}
