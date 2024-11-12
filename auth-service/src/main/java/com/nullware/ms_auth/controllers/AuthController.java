package com.nullware.ms_auth.controllers;

import com.nullware.ms_auth.dtos.requests.ForgotPasswordDTO;
import com.nullware.ms_auth.dtos.requests.LoginDTO;
import com.nullware.ms_auth.dtos.requests.RefreshTokenDTO;
import com.nullware.ms_auth.dtos.requests.ResetPasswordDTO;
import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import com.nullware.ms_auth.dtos.responses.ResetPasswordResponseDTO;
import com.nullware.ms_auth.dtos.responses.TokenResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interface that defines authentication-related endpoints for the application.
 * Provides methods for login, token refresh, logout, password recovery, and password reset.
 */
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication")
public interface AuthController {

    /**
     * Authenticates a user and provides a token upon successful login.
     *
     * @param loginDTO Data Transfer Object containing the user's login credentials.
     * @return A ResponseEntity containing the authentication result with a token.
     */
    @Operation(summary = "Log in to an existing user account",
            description = "Authenticates a user based on provided credentials and returns an access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO);

    /**
     * Refreshes an expired or nearly expired token, providing a new one.
     * Requires authentication.
     *
     * @param refreshTokenDTO Data Transfer Object containing the refresh token.
     * @return A ResponseEntity containing a new access token if the refresh is successful.
     */
    @Operation(summary = "Refresh user token",
            description = "Generates a new access token using a valid refresh token",
            security = {@SecurityRequirement(name = "Bearer Auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    @PostMapping("/refresh-token")
    ResponseEntity<TokenResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO);

    /**
     * Initiates the password recovery process for a user, sending a recovery email.
     *
     * @param forgotPasswordDTO Data Transfer Object containing the email address of the user requesting recovery.
     * @return A ResponseEntity indicating the result of the password recovery operation.
     */
    @Operation(summary = "Initiate password recovery",
            description = "Sends a password recovery email to the user associated with the provided email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password recovery email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid email or request data")
    })
    @PostMapping("/forgot-password")
    ResponseEntity<GenericResponseDTO> forgotPassword(@RequestBody @Valid ForgotPasswordDTO forgotPasswordDTO);

    /**
     * Allows an authenticated user to reset their password.
     *
     * @param resetPasswordDTO Data Transfer Object containing the new password details.
     * @return A ResponseEntity indicating the result of the password reset operation.
     */
    @Operation(summary = "Reset password",
            description = "Allows a user to change their password",
            security = {@SecurityRequirement(name = "Bearer Auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "User not authenticated")
    })
    @PostMapping("/reset-password")
    ResponseEntity<ResetPasswordResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO);
}
