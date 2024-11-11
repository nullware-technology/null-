package com.nullware.ms_auth.dtos.responses;

/**
 * Record for the response DTO of a successful login operation.
 */
public record LoginResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn) {
}
