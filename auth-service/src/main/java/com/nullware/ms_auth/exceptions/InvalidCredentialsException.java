package com.nullware.ms_auth.exceptions;

/**
 * Exception thrown when provided credentials are invalid during login.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
