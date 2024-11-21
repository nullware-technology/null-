package com.nullware.ms_auth.config;

import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import com.nullware.ms_auth.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions thrown by the authentication service, providing appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InvalidCredentialsException, indicating invalid login credentials were provided.
     *
     * @param ex the thrown InvalidCredentialsException
     * @return a response with a 401 status code and a message indicating invalid credentials
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<GenericResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ResponseEntity<>(new GenericResponseDTO("Invalid credentials provided"), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles AccountLockedException, indicating the user's account is locked due to multiple failed login attempts.
     *
     * @param ex the thrown AccountLockedException
     * @return a response with a 403 status code and a message indicating the account is locked
     */
    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<GenericResponseDTO> handleAccountLockedException(AccountLockedException ex) {
        return new ResponseEntity<>(new GenericResponseDTO("Account is locked"), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles InvalidTokenException, indicating the token provided is invalid or expired.
     *
     * @param ex the thrown InvalidTokenException
     * @return a response with a 401 status code and a message indicating the token is invalid or expired
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<GenericResponseDTO> handleInvalidTokenException(InvalidTokenException ex) {
        return new ResponseEntity<>(new GenericResponseDTO("Invalid or expired token"), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles UserNotAuthenticatedException, indicating an unauthenticated user attempted an action requiring authentication.
     *
     * @param ex the thrown UserNotAuthenticatedException
     * @return a response with a 401 status code and a message indicating the user is not authenticated
     */
    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<GenericResponseDTO> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex) {
        return new ResponseEntity<>(new GenericResponseDTO("User not authenticated"), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles EmailNotFoundException, indicating the email provided was not found in the system.
     *
     * @param ex the thrown EmailNotFoundException
     * @return a response with a 404 status code and a message indicating the email was not found
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<GenericResponseDTO> handleEmailNotFoundException(EmailNotFoundException ex) {
        return new ResponseEntity<>(new GenericResponseDTO("Email not found"), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles EmailSendingException, indicating an error occurred while attempting to send an email.
     *
     * @param ex the thrown EmailSendingException
     * @return a response with a 500 status code and a message indicating the email sending failure
     */
    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<GenericResponseDTO> handleEmailSendingException(EmailSendingException ex) {
        return new ResponseEntity<>(new GenericResponseDTO("Error sending email"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles PasswordComplexityException, indicating the provided password does not meet complexity requirements.
     *
     * @param ex the thrown PasswordComplexityException
     * @return a response with a 400 status code and a message indicating the password complexity requirement
     */
    @ExceptionHandler(PasswordComplexityException.class)
    public ResponseEntity<GenericResponseDTO> handlePasswordComplexityException(PasswordComplexityException ex) {
        return new ResponseEntity<>(new GenericResponseDTO("Password does not meet complexity requirements"), HttpStatus.BAD_REQUEST);
    }
}
