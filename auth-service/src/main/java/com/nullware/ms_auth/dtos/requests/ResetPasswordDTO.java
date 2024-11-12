package com.nullware.ms_auth.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDTO(
        @NotBlank String email,
        @NotBlank String oldPassword,
        @NotBlank String newPassword
) {

}
