package com.nullware.ms_auth.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ForgotPasswordDTO(@Email @NotNull String email) {
}
