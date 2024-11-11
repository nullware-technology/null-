package com.nullware.ms_auth.exceptions;

/**
 * Exception thrown when a user's account is locked due to too many failed login attempts.
 */
public class AccountLockedException extends RuntimeException {
    public AccountLockedException(String message) {
        super(message);
    }
}
