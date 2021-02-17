package com.demo.test.service.mapper;

import com.demo.test.exceptions.Errors;
import com.demo.test.exceptions.UserExceptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MapperTest {

  @Autowired
  private Mapper mapper;

  @Test
  void givenDateOfBirth_whenBadValue_thenThrowBadRequestExceptionWithError() {

    UserExceptions.BadRequestException badRequestException =
        assertThrows(UserExceptions.BadRequestException.class, () -> mapper.getDateOfBirth("32-01-1980"));
    assertEquals(Errors.INPUT_DATE_FORMAT, badRequestException.getInternalError());
  }
}
