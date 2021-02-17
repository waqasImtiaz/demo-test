package com.demo.test.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * customized response object in
 * case of error
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class ApiErrorDto {

  private HttpStatus status;
  private int errorNumber;
  private String timeStamp;
  private List<String> messages;
  private String debugMessage;

}
