package com.airfranceklm.test.service.user;

import com.airfranceklm.test.dto.UserDto;
import com.airfranceklm.test.dto.mapper.Mapper;
import com.airfranceklm.test.exceptions.Errors;
import com.airfranceklm.test.exceptions.UserExceptions;
import com.airfranceklm.test.model.User;
import com.airfranceklm.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

/**
 * class responsible for all the
 * user operations
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * registers a user
   * @param user input object of type User
   * @return if the user already not present in
   * database it is created and returned
   *        else throws custom BadRequestException
   *        if user already present of its age is < 18
   */
  @Override
  @Transactional
  public User registerUser(User user) {
    //check if user already exists by email
    if (getUser(user.getEmail()) != null) {
      throw new UserExceptions.BadRequestException("Email [" + user.getEmail() + "] already exists.",
          Errors.USER_ALREADY_EXISTS);
    }
    if (calculateAge(user.getDateOfBirth()) < 18) {
      throw new UserExceptions.BadRequestException("Age [" + calculateAge(user.getDateOfBirth())
          + "] is below 18. Only 18 and above users can register.",
          Errors.UNDER_AGE);
    }
    user.setPassword(encryptPassword(user.getPassword()));
    return userRepository.save(user);
  }

  /**
   * returns a user by email
   * @param email input email
   * @return retrieved user object
   */
  @Override
  public User getUser(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * finds user by id
   * @param id input id which is the id in database
   * @return Optional<User> could be empty if id
   *         does not exists.
   */
  @Override
  public Optional<User> getUser(long id) {
    return userRepository.findById(id);
  }

  /**
   * converts userDto to user Object
   * @param userDto input
   * @return
   */
  @Override
  public User toUser(UserDto userDto) {
    return Mapper.toUser(userDto);
  }

  /**
   * encodes password
   * @param password
   * @return
   */
  private String encryptPassword(String password) {
    return password;//new BCryptPasswordEncoder().encode(password);
  }

  /**
   * calculates age in years
   * @param dateOfBirth input
   * @return total age
   */
  private int calculateAge(LocalDate dateOfBirth) {
    return Period.between(dateOfBirth, LocalDate.now()).getYears();

  }
}
