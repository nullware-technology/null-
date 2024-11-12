package com.nullware.ms_auth.exceptions;

/**
 * Exception thrown when an unauthenticated user attempts to perform an action requiring authentication.
 */
public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}