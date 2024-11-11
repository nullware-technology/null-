package com.nullware.ms_auth.exceptions;

/**
 * Exception thrown when the provided password does not meet complexity requirements.
 */
public class PasswordComplexityException extends RuntimeException {
  public PasswordComplexityException(String message) {
    super(message);
  }
}
