package com.demo.test.exceptions;

/**
 * internal error codes for each error
 * (useful during maintenance)
 */
public class Errors {

  // internal server errors
  public static final int USER_REGISTRATION_FAILED = 513;
  public static final int UNKNOWN_INTERNAL_SERVER_ERROR = 517;

  // bad request internal errors
  public static final int USER_ALREADY_EXISTS = 453;
  public static final int CONSTRAINT_VIOLATION = 457;
  public static final int USER_DOEST_EXISTS = 459;
  public static final int UNDER_AGE = 461;
  public static final int UNKNOWN_BAD_REQUEST = 463;
  public static final int INPUT_DATE_FORMAT = 465;
}
