package com.demo.test.exceptions;

import com.demo.test.dto.ApiErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * global exception handler
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


  private final DateTimeFormatter dateTimeFormatter;

  @Autowired
  public RestExceptionHandler(@Value("${date.output.format}") final String dateTimeFormat) {
    dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
  }


  /**
   * handles all the BadRequestExceptions and returns
   * a customized response with bad request status code
   * and error information
   * @param badRequestException input exception object
   * @return
   */
  @ExceptionHandler(value = {UserExceptions.BadRequestException.class, UserExceptions.InternalServerException.class})
  public ResponseEntity<Object> handleCustomBadRequestException(UserExceptions.BadRequestException
                                                                            badRequestException) {
    List<String> errors = new ArrayList<>();
    errors.add(badRequestException.getMessage());
    ApiErrorDto apiErrorDto = ApiErrorDto.builder().status(HttpStatus.BAD_REQUEST)
        .errorNumber(badRequestException.getInternalError()).timeStamp(LocalDateTime.now().format(dateTimeFormatter))
        .messages(errors).debugMessage(badRequestException.getLocalizedMessage()).build();
    ResponseEntity<Object> responseEntity = new ResponseEntity<>(apiErrorDto, HttpStatus.BAD_REQUEST);
    logger.error("Output: " + responseEntity);
    return responseEntity;
  }

  /**
   * handles all the constraint violations and returns
   * a customized response with bad request status code and error
   * information
   * @param constraintViolationException
   * @return
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException
                                                                            constraintViolationException) {
    List<String> errorMessages = constraintViolationException.getConstraintViolations().stream()
        .map(ConstraintViolation::getMessage).collect(Collectors.toList());
    ApiErrorDto apiErrorDto = ApiErrorDto.builder().status(HttpStatus.BAD_REQUEST)
        .errorNumber(Errors.CONSTRAINT_VIOLATION).timeStamp(LocalDateTime.now().format(dateTimeFormatter))
        .messages(errorMessages).debugMessage(constraintViolationException.getLocalizedMessage()).build();
    ResponseEntity<Object> responseEntity = new ResponseEntity<>(apiErrorDto, HttpStatus.BAD_REQUEST);
    logger.error("Output: " + responseEntity);
    return responseEntity;
  }

  /**
   * handles all the constraint violations and returns
   * a customized response with bad request status code and error
   * information
   * @param ex
   * @param headers
   * @param status
   * @param request
   * @return
   */
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                             HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    ApiErrorDto apiErrorDto = ApiErrorDto.builder().status(HttpStatus.BAD_REQUEST)
        .errorNumber(Errors.CONSTRAINT_VIOLATION).timeStamp(LocalDateTime.now().format(dateTimeFormatter))
        .messages(errors).build();
    ResponseEntity<Object> responseEntity = new ResponseEntity<>(apiErrorDto, HttpStatus.BAD_REQUEST);
    logger.error("Output: " + responseEntity);
    return responseEntity;
  }


  /**
   * handles all remaining exceptions
   * @param ex
   * @param request
   * @param response
   * @return
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> handle(Exception ex,
                                       HttpServletRequest request, HttpServletResponse response) {
    ApiErrorDto apiErrorDto;
    List<String> errorMessages = new ArrayList<>();
    ResponseEntity<Object> responseEntity;
    errorMessages.add(ex.getMessage());
    if (ex instanceof NullPointerException) {
      apiErrorDto = ApiErrorDto.builder().status(HttpStatus.BAD_REQUEST).errorNumber(Errors.UNKNOWN_BAD_REQUEST)
          .messages(errorMessages).timeStamp(LocalDateTime.now().format(dateTimeFormatter)).build();
      responseEntity = new ResponseEntity<>(apiErrorDto,HttpStatus.BAD_REQUEST);
      logger.error("Output: " + responseEntity);
      return responseEntity;
    }
    apiErrorDto = ApiErrorDto.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).errorNumber(Errors.UNKNOWN_INTERNAL_SERVER_ERROR)
        .messages(errorMessages).timeStamp(LocalDateTime.now().format(dateTimeFormatter)).build();
    responseEntity = new ResponseEntity<>(apiErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    logger.error("Output: " + responseEntity);
    return responseEntity;
  }
}
