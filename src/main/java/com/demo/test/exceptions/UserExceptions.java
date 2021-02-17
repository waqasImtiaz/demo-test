package com.demo.test.exceptions;

public class UserExceptions {

  /**
   * exception for all invalid requests
   * received from user
   */
  public static class BadRequestException extends BaseException {

    public BadRequestException(String message, int internalError) {
      super(message, internalError);
    }
  }

  /**
   * exception for all internal errors
   * should be hidden from service users
   */
  public static class InternalServerException extends BaseException {

    public InternalServerException(String message, int internalError) {
      super(message, internalError);
    }
  }
}
