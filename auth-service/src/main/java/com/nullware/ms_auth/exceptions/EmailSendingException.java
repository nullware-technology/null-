package com.nullware.ms_auth.exceptions;

/**
 * Exception thrown when there is an error sending an email, such as during password recovery.
 */
public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message) {
        super(message);
    }
}
