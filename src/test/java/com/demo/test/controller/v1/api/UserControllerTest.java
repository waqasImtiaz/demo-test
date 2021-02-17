package com.demo.test.controller.v1.api;

import com.demo.test.dto.ApiSuccessDto;
import com.demo.test.dto.UserDto;
import com.demo.test.exceptions.Errors;
import com.demo.test.exceptions.UserExceptions;
import com.demo.test.model.User;
import com.demo.test.service.mapper.Mapper;
import com.demo.test.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MockBean(classes = UserService.class)
class UserControllerTest {

  private final UserService userService;

  private final UserController userController;

  private final DateTimeFormatter dateTimeFormatter;

  private final Mapper mapper;

  @Autowired
  public UserControllerTest (@Value("${date.output.format}") final String dateTimeFormat, UserController userController,
                             UserService userService, Mapper mapper) {
    dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    this.userController = userController;
    this.userService = userService;
    this.mapper = mapper;
  }

  @Test
  void givenUserId_whenUserExists_thenReturnUserTest() {
    User waqas = getWaqas();

    Mockito.when(userService.getUser(waqas.getId())).thenReturn(Optional.of(waqas));
    ResponseEntity<Object> expectedResult = getResponseEntityWithOkAndBody(waqas);
    ResponseEntity<Object> actualResult = userController.getUser(waqas.getId());

    Assertions.assertEquals((ApiSuccessDto) expectedResult.getBody(), (ApiSuccessDto) actualResult.getBody());
  }

  @Test
  void givenUserId_whenUserExists_thenReturnStatusOkTest() {
    User waqas = getWaqas();

    Mockito.when(userService.getUser(waqas.getId())).thenReturn(Optional.of(waqas));
    ResponseEntity<Object> expectedResult = getResponseEntityWithOkAndBody(waqas);
    ResponseEntity<Object> actualResult = userController.getUser(waqas.getId());

    assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
  }

  @Test
  void givenUserId_whenUserExists_thenReturnResourceUriSameTest() {
    User waqas = getWaqas();

    Mockito.when(userService.getUser(waqas.getId())).thenReturn(Optional.of(waqas));
    ResponseEntity<Object> expectedResult = getResponseEntityWithOkAndBody(waqas);
    ResponseEntity<Object> actualResult = userController.getUser(waqas.getId());

    assertEquals(((ApiSuccessDto)expectedResult.getBody()).getResourceUri(), ((ApiSuccessDto)actualResult.getBody())
        .getResourceUri());
  }

  @Test
  void givenUserId_whenUserNotExists_thenThrowBadRequestExceptionTest() {
    User waqas = getWaqas();

    Mockito.when(userService.getUser(waqas.getId())).thenReturn(Optional.empty());
    UserExceptions.BadRequestException exception = assertThrows(UserExceptions.BadRequestException.class, () ->
        userController.getUser(waqas.getId()));

    assertTrue(exception.getMessage().contains("User with id [" + waqas.getId() + "] does not exists"));
  }

  @Test
  void givenUserId_whenUserNotExists_thenReturnErrorUserDoesNotExistsTest() {
    User waqas = getWaqas();

    Mockito.when(userService.getUser(waqas.getId())).thenReturn(Optional.empty());
    UserExceptions.BadRequestException exception = assertThrows(UserExceptions.BadRequestException.class, () ->
        userController.getUser(waqas.getId()));

    assertEquals(Errors.USER_DOEST_EXISTS, exception.getInternalError());
  }

  @Test
  void givenUser_whenUserIsValid_thenReturnUserTest() {
    User waqas = getWaqas();
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    Mockito.when(userService.toUser(waqasDto)).thenReturn(waqas);
    Mockito.when(userService.registerUser(waqas)).thenReturn(waqas);
    ResponseEntity<Object> expectedResult = getResponseEntityWithCreateAndBody(waqas);
    ResponseEntity<Object> actualResult = userController.registerUser(waqasDto, "v1");

    assertEquals((ApiSuccessDto)expectedResult.getBody(), (ApiSuccessDto)actualResult.getBody());
  }

  @Test
  void givenUser_whenUserIsValid_thenReturnStatusCreatedTest() {
    User waqas = getWaqas();
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    Mockito.when(userService.toUser(waqasDto)).thenReturn(waqas);
    Mockito.when(userService.registerUser(waqas)).thenReturn(waqas);
    ResponseEntity<Object> expectedResult = getResponseEntityWithCreateAndBody(waqas);
    ResponseEntity<Object> actualResult = userController.registerUser(waqasDto, "v1");

    assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
  }

  @Test
  void givenUser_whenUserIsValid_thenReturnCreatedUriTest() {
    User waqas = getWaqas();
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    Mockito.when(userService.toUser(waqasDto)).thenReturn(waqas);
    Mockito.when(userService.registerUser(waqas)).thenReturn(waqas);
    ResponseEntity<Object> expectedResult = getResponseEntityWithCreateAndBody(waqas);
    ResponseEntity<Object> actualResult = userController.registerUser(waqasDto, "v1");

    assertEquals(((ApiSuccessDto)expectedResult.getBody()).getResourceUri(), ((ApiSuccessDto)actualResult.getBody())
        .getResourceUri());
  }

  @Test
  void givenUserId_whenUserNotExistsAndRegistrationFailed_thenThrowInternalServerExceptionTest() {
    User waqas = getWaqas();
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    Mockito.when(userService.toUser(waqasDto)).thenReturn(waqas);
    Mockito.when(userService.getUser(waqas.getId())).thenReturn(Optional.empty());
    UserExceptions.InternalServerException exception = assertThrows(UserExceptions.InternalServerException.class, () ->
        userController.registerUser(waqasDto, "v1"));

    assertTrue(exception.getMessage().contains("User registration failed."));
  }

  @Test
  void givenUserId_whenUserNotExistsAndRegistrationFailed_thenReturnErrorUserRegistrationFailedStatusTest() {
    User waqas = getWaqas();
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();

    Mockito.when(userService.toUser(waqasDto)).thenReturn(waqas);
    Mockito.when(userService.getUser(waqas.getId())).thenReturn(Optional.empty());
    UserExceptions.InternalServerException exception = assertThrows(UserExceptions.InternalServerException.class, () ->
        userController.registerUser(waqasDto, "v1"));

    assertEquals(Errors.USER_REGISTRATION_FAILED, exception.getInternalError());
  }

  private User getWaqas() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();
    return mapper.toUser(waqasDto);
  }

  private ResponseEntity<Object> getResponseEntityWithOkAndBody(User waqas) {
    return ResponseEntity.ok(ApiSuccessDto.builder().object(mapper.toUserDto(waqas))
        .timeStamp(LocalDateTime.now().format(dateTimeFormatter)).status(HttpStatus.OK)
        .resourceUri("/v1/users/" + waqas.getId()).build());

  }

  private ResponseEntity<Object> getResponseEntityWithCreateAndBody(User waqas) {
    ApiSuccessDto apiSuccessDto = ApiSuccessDto.builder().object(mapper.toUserDto(waqas))
        .timeStamp(LocalDateTime.now().format(dateTimeFormatter)).status(HttpStatus.CREATED)
        .resourceUri("/v1/users/" + waqas.getId()).build();
    return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessDto);
  }
}
