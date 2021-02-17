package com.airfranceklm.test.controller.v1.api;

import com.airfranceklm.test.config.Swagger2Config;
import com.airfranceklm.test.dto.ApiSuccessDto;
import com.airfranceklm.test.dto.UserDto;
import com.airfranceklm.test.dto.mapper.Mapper;
import com.airfranceklm.test.exceptions.Errors;
import com.airfranceklm.test.exceptions.UserExceptions;
import com.airfranceklm.test.model.User;
import com.airfranceklm.test.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * main controller receives input calls
 */
@RestController
@RequestMapping("/v1")
@Api(value = Swagger2Config.TITLE, tags = {Swagger2Config.PROJECT_DESCRIPTION})
@Validated
public class UserController {

  private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  private final UserService userService;
  private static final Logger logger = LogManager.getLogger(UserController.class);

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }


  /**
   * registers a user in the database after
   * performing validations on it. If validations
   * or user creation fails then throws exceptions
   * @param userDto external input received in call
   * @param version request parameter used for version
   * @return user if registration was successful
   */
  @PostMapping("/register")
  @ApiOperation(value = "Registers-a-user", notes = "Registers a user and returns the registered user.")
  public ResponseEntity<Object> registerUser(@RequestBody @Valid UserDto userDto, @RequestParam(name = "version",
      defaultValue="v1") String version ) {
    logger.info(String.format("Input received: %s", userDto.toString()));

    User user = userService.toUser(userDto);
    User registeredUser = userService.registerUser(user);

    if (registeredUser != null) {
      ApiSuccessDto apiSuccessDto = ApiSuccessDto.builder().object(Mapper.toUserDto(registeredUser))
          .timeStamp(LocalDateTime.now().format(dateTimeFormatter)).status(HttpStatus.CREATED)
          .resourceUri("/v1/users/" + registeredUser.getId()).build();
      ResponseEntity<Object> responseEntity= ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessDto);
      logger.info(String.format("Output: %s", responseEntity));
      return responseEntity;
    } else {
      throw new UserExceptions.InternalServerException("User registration failed.", Errors.USER_REGISTRATION_FAILED);
    }
  }

  /**
   * retrieves a user based on input id.
   * id cannot be -ve. Throws exception if
   * validation fails or user does not exist
   * @param id pathVariable received in request
   * @return user object if found
   */
  @GetMapping("/users/{id}")
  @ApiOperation(value = "Return-user-by-id", notes = "Returns a user found by the input {id}")
  public ResponseEntity<Object> getUser( @PathVariable(name = "id") @Min(value = 0, message = "id must be greater than 0") Long id) {

    logger.info(String.format("Id received at users/{id}: %d", id));
    Optional<User> user = userService.getUser(id);
    if (user.isPresent()) {
      ApiSuccessDto apiSuccessDto = ApiSuccessDto.builder().object(Mapper.toUserDto(user.get()))
          .timeStamp(LocalDateTime.now().format(dateTimeFormatter)).status(HttpStatus.OK)
          .resourceUri("/v1/users/" + user.get().getId()).build();
      ResponseEntity<Object> responseEntity= ResponseEntity.ok(apiSuccessDto);
      logger.info(String.format("Output: %s", responseEntity));
      return responseEntity;
    } else {
      throw new UserExceptions.BadRequestException("User with id [" + id + "] does not exists", Errors.USER_DOEST_EXISTS);
    }
  }
}
