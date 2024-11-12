package com.nullware.ms_auth.exceptions;

/**
 * Exception thrown when an email is not found in the system during password recovery.
 */
public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
