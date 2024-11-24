package com.nullware.ms_auth.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password) {
}
