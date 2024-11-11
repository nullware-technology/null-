package com.nullware.ms_auth.services;

import com.nullware.ms_auth.dtos.requests.ForgotPasswordDTO;
import com.nullware.ms_auth.dtos.requests.LoginDTO;
import com.nullware.ms_auth.dtos.requests.RefreshTokenDTO;
import com.nullware.ms_auth.dtos.requests.ResetPasswordDTO;
import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import com.nullware.ms_auth.dtos.responses.LoginResponseDTO;
import com.nullware.ms_auth.dtos.responses.RefreshTokenResponseDTO;
import com.nullware.ms_auth.dtos.responses.ResetPasswordResponseDTO;
import com.nullware.ms_auth.exceptions.*;

/**
 * Interface that defines authentication-related services for handling login, token refresh,
 * logout, password recovery, and password reset functionality.
 */
public interface AuthService {

    /**
     * Authenticates a user based on provided credentials and returns an access token and refresh token.
     *
     * @param loginDTO Data Transfer Object containing the user's login credentials.
     * @return A LoginResponseDTO containing the access token, refresh token, token type, and expiration details.
     * @throws InvalidCredentialsException if the provided credentials are incorrect.
     * @throws AccountLockedException if the user account is locked due to too many failed login attempts.
     */
    LoginResponseDTO login(LoginDTO loginDTO) throws InvalidCredentialsException, AccountLockedException;

    /**
     * Refreshes an expired or nearly expired access token, providing a new access token and refresh token.
     *
     * @param refreshTokenDTO Data Transfer Object containing the refresh token.
     * @return A RefreshTokenResponseDTO containing the new access token, refresh token, token type, and expiration details.
     * @throws InvalidTokenException if the provided refresh token is invalid or expired.
     */
    RefreshTokenResponseDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws InvalidTokenException;

    /**
     * Logs out the authenticated user by invalidating their current session.
     *
     * @return A GenericResponseDTO containing a message indicating the result of the logout operation.
     * @throws UserNotAuthenticatedException if the user is not authenticated or their session is already invalid.
     */
    GenericResponseDTO logout() throws UserNotAuthenticatedException;

    /**
     * Initiates the password recovery process by sending a password recovery email to the user.
     *
     * @param forgotPasswordDTO Data Transfer Object containing the email address of the user requesting password recovery.
     * @return A GenericResponseDTO containing a message indicating the result of the password recovery operation.
     * @throws EmailNotFoundException if the provided email is not associated with any account.
     * @throws EmailSendingException if there is an error sending the recovery email.
     */
    GenericResponseDTO forgotPassword(ForgotPasswordDTO forgotPasswordDTO) throws EmailNotFoundException, EmailSendingException;

    /**
     * Resets the user's password using the provided reset token and new password details.
     *
     * @param resetPasswordDTO Data Transfer Object containing the reset token and new password.
     * @return A ResetPasswordResponseDTO indicating the result of the password reset operation.
     * @throws InvalidTokenException if the provided reset token is invalid or expired.
     * @throws PasswordComplexityException if the new password does not meet security requirements.
     */
    ResetPasswordResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws InvalidTokenException, PasswordComplexityException;
}
