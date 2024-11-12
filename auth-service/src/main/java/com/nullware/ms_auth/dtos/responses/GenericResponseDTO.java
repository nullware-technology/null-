package com.nullware.ms_auth.dtos.responses;

/**
 * Generic response DTO for operations like logout and password recovery.
 */
public record GenericResponseDTO(
        String message
) {
}
