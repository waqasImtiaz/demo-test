package com.airfranceklm.test.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

  private String status;
  private int errorNumber;
  private String timeStamp;
  private List<String> messages;
  private String debugMessage;

}
