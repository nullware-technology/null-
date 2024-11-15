package com.nullware.ms_users.mappers;

import com.nullware.ms_users.dtos.requests.RegisterRequestDTO;
import com.nullware.ms_users.dtos.responses.UserInfoResponse;
import com.nullware.ms_users.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@UtilityClass
public class UserMapper {

    public static User toUser(RegisterRequestDTO userDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(userDTO);
        return User.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(passwordEncoder.encode(userDTO.password()))
                .build();
    }

    public static UserInfoResponse toUserInfoResponse(User user) {
        return UserInfoResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt().toString())
                .subscriptionPlanName(user.getSubscriptionPlanName())
                .build();
    }
}
