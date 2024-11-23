package com.nullware.ms_users.dtos.responses;

import lombok.Builder;

@Builder
public record UserInfoResponseDTO(String name, String email, String createdAt, String subscriptionPlanName) {
}
