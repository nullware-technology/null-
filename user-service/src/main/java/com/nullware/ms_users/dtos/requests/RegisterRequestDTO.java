package com.nullware.ms_users.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password) {
}