package com.nullware.ms_auth.dtos.responses;

/**
 * Record for the response DTO of a successful refresh token operation.
 */
public record TokenResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType,
        long accessExpiresIn,
        long refreshExpiresIn
) {
}
