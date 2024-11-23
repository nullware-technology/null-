package com.nullware.ms_users.dtos;

import com.nullware.ms_users.entity.Plan;
import jakarta.validation.constraints.NotBlank;

public record UserAuthDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        Plan plan) {
}
