package com.demo.test.exception;

import com.demo.test.dto.ApiErrorDto;
import com.demo.test.exceptions.Errors;
import com.demo.test.exceptions.RestExceptionHandler;
import com.demo.test.exceptions.UserExceptions;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestExceptionHandlerTest {

  @Autowired
  private RestExceptionHandler restExceptionHandler;

  @Test
  void givenCustomException_whenException_thenReturnBadRequestStatus() {
    UserExceptions.BadRequestException badRequestException =
        new UserExceptions.BadRequestException("", 0);
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleCustomBadRequestException(badRequestException);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void givenCustomException_whenException_thenReturnMessage() {
    String message = "test message";
    UserExceptions.BadRequestException badRequestException =
        new UserExceptions.BadRequestException(message, 0);
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleCustomBadRequestException(badRequestException);
    assertEquals(message, ((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getMessages().get(0));
  }

  @Test
  void givenCustomException_whenException_thenReturnErrorNumber() {
    UserExceptions.BadRequestException badRequestException =
        new UserExceptions.BadRequestException("", 0);
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleCustomBadRequestException(badRequestException);
    assertEquals(0, ((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getErrorNumber());
  }

  @Test
  void givenCustomException_whenException_thenReturnTime() {
    UserExceptions.BadRequestException badRequestException =
        new UserExceptions.BadRequestException("", 0);
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleCustomBadRequestException(badRequestException);
    assertTrue(StringUtils.isNotBlank(((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getTimeStamp()));
  }

  @Test
  void givenCustomException_whenException_thenReturnDebugMessage() {
    UserExceptions.BadRequestException badRequestException =
        new UserExceptions.BadRequestException("", 0);
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleCustomBadRequestException(badRequestException);
    assertEquals(badRequestException.getLocalizedMessage(), ((ApiErrorDto) responseEntity.getBody()).getDebugMessage());
  }

  @Test
  void givenConstraintViolationException_whenException_thenReturnBadRequestStatus() {
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException("Test message", new HashSet<>());
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleConstraintViolationException(constraintViolationException);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void givenConstraintViolationException_whenException_thenReturnErrorMessage() {
    String message = "Test message";
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException("", getConstraintViolations(message));
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleConstraintViolationException(constraintViolationException);
    assertEquals(message, ((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getMessages().get(0));
  }

  @Test
  void givenConstraintViolationException_whenException_thenReturnErrorNumber() {
    String message = "Test message";
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException(message, new HashSet<>());
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleConstraintViolationException(constraintViolationException);
    assertEquals(Errors.CONSTRAINT_VIOLATION, ((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getErrorNumber());
  }

  @Test
  void givenConstraintViolationException_whenException_thenReturnTimeStamp() {
    String message = "Test message";
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException(message, new HashSet<>());
    ResponseEntity<Object> responseEntity = restExceptionHandler.handleConstraintViolationException(constraintViolationException);
    assertTrue(StringUtils.isNotBlank(((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getTimeStamp()));
  }

  @Test
  void givenException_whenNullPointerException_thenReturnBadRequestStatus() {
    ResponseEntity<Object> responseEntity = restExceptionHandler.handle(new NullPointerException(), null, null);
    assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
  }

  @Test
  void givenException_whenNullPointerException_thenReturnErrorNumber() {
    ResponseEntity<Object> responseEntity = restExceptionHandler.handle(new NullPointerException(),
        null, null);
    assertEquals(Errors.UNKNOWN_BAD_REQUEST,((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getErrorNumber());
  }

  @Test
  void givenException_whenNullPointerException_thenReturnErrorMessage() {
    String message = "Null pointer exception";
    ResponseEntity<Object> responseEntity = restExceptionHandler.handle(new NullPointerException(message),
        null, null);
    assertEquals(message, ((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).
        getMessages().get(0));
  }

  @Test
  void givenException_whenNullPointerException_thenReturnTimeStamp() {
    ResponseEntity<Object> responseEntity = restExceptionHandler.handle(new NullPointerException(),
        null, null);
    assertTrue(StringUtils.isNotBlank(((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getTimeStamp()));
  }

  @Test
  void givenException_whenExcetion_thenReturnInternalServerError() {
    ResponseEntity<Object> responseEntity = restExceptionHandler.handle(new Exception(), null, null);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,responseEntity.getStatusCode());
  }

  @Test
  void givenException_whenException_thenReturnErrorNumber() {
    ResponseEntity<Object> responseEntity = restExceptionHandler.handle(new Exception(),
        null, null);
    assertEquals(Errors.UNKNOWN_INTERNAL_SERVER_ERROR,((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).
        getErrorNumber());
  }

  @Test
  void givenException_whenException_thenReturnErrorMessage() {
    String message = "Any other exception";
    ResponseEntity<Object> responseEntity = restExceptionHandler.handle(new Exception(message),
        null, null);
    assertEquals(message, ((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getMessages().
        get(0));
  }

  @Test
  void givenException_whenException_thenReturnTimeStamp() {
    ResponseEntity<Object> responseEntity = restExceptionHandler.handle(new Exception(),
        null, null);
    assertTrue(StringUtils.isNotBlank(((ApiErrorDto) Objects.requireNonNull(responseEntity.getBody())).getTimeStamp()));
  }

  private Set<ConstraintViolation<Object>> getConstraintViolations(String message) {
    Set<ConstraintViolation<Object>> constraintViolations = new HashSet<>();
    constraintViolations.add(new ConstraintViolation<Object>() {
      @Override
      public String getMessage() {
        return message;
      }

      @Override
      public String getMessageTemplate() {
        return null;
      }

      @Override
      public Object getRootBean() {
        return null;
      }

      @Override
      public Class<Object> getRootBeanClass() {
        return null;
      }

      @Override
      public Object getLeafBean() {
        return null;
      }

      @Override
      public Object[] getExecutableParameters() {
        return new Object[0];
      }

      @Override
      public Object getExecutableReturnValue() {
        return null;
      }

      @Override
      public Path getPropertyPath() {
        return null;
      }

      @Override
      public Object getInvalidValue() {
        return null;
      }

      @Override
      public ConstraintDescriptor<?> getConstraintDescriptor() {
        return null;
      }

      @Override
      public <U> U unwrap(Class<U> aClass) {
        return null;
      }
    });
    return constraintViolations;
  }
}
