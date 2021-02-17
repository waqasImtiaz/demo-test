package com.airfranceklm.test.service.user;

import com.airfranceklm.test.dto.UserDto;
import com.airfranceklm.test.dto.mapper.Mapper;
import com.airfranceklm.test.exceptions.UserExceptions;
import com.airfranceklm.test.model.User;
import com.airfranceklm.test.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

  @Autowired
  @MockBean
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Test
  void getUser_whenUserExists_thenReturnUserTest() {

    User waqas = getWaqas();

    Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(waqas));

    assertEquals(waqas, userService.getUser(0L).get());

  }

  @Test
  void getUser_whenUserNotExists_thenReturnNullTest() {
    User waqas = null;
    Mockito.when(userRepository.findById(0L)).thenReturn(Optional.ofNullable(waqas));
    assertFalse(userService.getUser(0L).isPresent());
  }

  @Test
  void getUserByEmail_whenUserExists_thenReturnUserTest() {

    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();
    User waqas = Mapper.toUser(waqasDto);

    Mockito.when(userRepository.findByEmail(waqas.getEmail())).thenReturn(waqas);

    assertEquals(waqas, userService.getUser(waqas.getEmail()));

  }

  @Test
  void getUserByEmail_whenUserNotExists_thenReturnNullTest() {
    User waqas = getWaqas();
    Mockito.when(userRepository.findByEmail(waqas.getEmail())).thenReturn(null);
    assertNull(userService.getUser(waqas.getEmail()));
  }

  @Test
  void registerUser_whenUserExists_thenThrowBadRequestExceptionTest() {
    User waqas = getWaqas();
    String expectedMessage = "Email [" + waqas.getEmail() + "] already exists.";

    Mockito.when(userRepository.findByEmail(waqas.getEmail())).thenReturn(waqas);

    Exception exception = assertThrows(UserExceptions.BadRequestException.class, () -> userService.registerUser(waqas));

    assertTrue(exception.getMessage().contains(expectedMessage));
  }

  // pay attention here age should be <= 18
  @Test
  void registerUser_whenUserNotExistsAndIsAdult_thenCreateUserTest() {
    User waqas = getWaqas();

    Mockito.when(userRepository.findByEmail(waqas.getEmail())).thenReturn(null);
    Mockito.when(userRepository.save(waqas)).thenReturn(waqas);

    assertEquals(waqas, userService.registerUser(waqas));
  }

  @Test
  void registerUser_whenUserNotAdult_thenThrowBadRequestExceptionTest() {
    User waqas = getWaqas();
    waqas.setDateOfBirth(LocalDate.of(2015, 1, 1));
    String expectedMessage = "Age [" + calculateAge(waqas.getDateOfBirth())
        + "] is below 18. Only 18 and above users can register.";

    Mockito.when(userRepository.findByEmail(waqas.getEmail())).thenReturn(null);

    Exception exception = assertThrows(UserExceptions.BadRequestException.class, () -> userService.registerUser(waqas));

    assertTrue(exception.getMessage().contains(expectedMessage));
  }

  private int calculateAge(LocalDate dateOfBirth) {
    return Period.between(dateOfBirth, LocalDate.now()).getYears();
  }

  private User getWaqas() {
    UserDto waqasDto = UserDto.builder().firstName("waqas").lastName("imtiaz").dateOfBirth("10-10-1985")
        .password("******").email("waqas@gmail.com").sex("MALE").country("france").phoneNumber("0981797848")
        .build();
    return Mapper.toUser(waqasDto);
  }
}
