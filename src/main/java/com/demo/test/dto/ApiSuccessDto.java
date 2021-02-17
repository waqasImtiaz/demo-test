package com.demo.test.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * customized response object in
 * case of success
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class ApiSuccessDto {
  private HttpStatus status;
  private String timeStamp;
  private String resourceUri;
  /**
   * object that should be returned
   * incase of success
   */
  private Object object;
}
