package com.nullware.ms_users.security;

import com.nullware.ms_users.entity.User;
import com.nullware.ms_users.models.Plans;
import com.nullware.ms_users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService {
    final UserRepository userRepo;

    public UserDetails loadUserByUsername(String username) {
        User user = this.userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Plans.getAuthoritiesByPlan(user.getSubscriptionPlanName())
        );
    }
}
