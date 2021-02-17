package com.demo.test.service.user;

import com.demo.test.dto.UserDto;
import com.demo.test.model.User;

import java.util.Optional;

/**
 * interface to be used for
 * user operations
 */
public interface UserService {

  User toUser(UserDto userDto);
  User registerUser(User user);
  User getUser(String email);
  Optional<User> getUser(long id);
}
