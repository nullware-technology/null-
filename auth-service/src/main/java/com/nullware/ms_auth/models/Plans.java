package com.nullware.ms_auth.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class Plans {
    public static final String ROLE_FREE = "ROLE_FREE";
    public static final String ROLE_BASIC = "ROLE_BASIC";
    public static final String ROLE_PREMIUM = "ROLE_PREMIUM";

    public static final List<SimpleGrantedAuthority> FREE_AUTHORITIES = List.of(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority(ROLE_FREE)
    );

    public static final List<SimpleGrantedAuthority> BASIC_AUTHORITIES = List.of(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority(ROLE_BASIC)
    );

    public static final List<SimpleGrantedAuthority> PREMIUM_AUTHORITIES = List.of(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority(ROLE_PREMIUM)
    );

    public static List<SimpleGrantedAuthority> getAuthoritiesByPlan(String planType) {
        return switch (planType.toLowerCase()) {
            case "premium" -> PREMIUM_AUTHORITIES;
            case "basic" -> BASIC_AUTHORITIES;
            case "free" -> FREE_AUTHORITIES;
            default -> throw new IllegalArgumentException("Invalid plan type");
        };
    }
}
