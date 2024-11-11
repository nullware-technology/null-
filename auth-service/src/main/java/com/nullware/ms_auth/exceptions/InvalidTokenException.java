package com.nullware.ms_auth.exceptions;

/**
 * Exception thrown when a provided token is invalid or expired.
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
