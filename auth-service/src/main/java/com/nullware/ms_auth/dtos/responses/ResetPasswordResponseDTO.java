package com.nullware.ms_auth.dtos.responses;

/**
 * Record for the response DTO of a successful password reset operation.
 */
public record ResetPasswordResponseDTO(
        String message,
        boolean success
) {
}
