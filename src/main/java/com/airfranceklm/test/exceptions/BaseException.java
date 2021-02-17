package com.airfranceklm.test.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseException extends RuntimeException{

  private String message;
  private int internalError;
}
