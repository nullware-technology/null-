package com.nullware.ms_users.entity.enums;

import lombok.Getter;

@Getter
public enum SubscriptionStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    CANCELED("Canceled"),
    EXPIRED("Expired"),
    PENDING("Pending");

    private final String description;

    SubscriptionStatus(String description) {
        this.description = description;
    }

}