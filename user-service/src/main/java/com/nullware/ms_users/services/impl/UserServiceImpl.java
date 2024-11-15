package com.nullware.ms_users.services.impl;

import com.nullware.ms_users.dtos.requests.RegisterRequestDTO;
import com.nullware.ms_users.dtos.responses.GenericResponse;
import com.nullware.ms_users.dtos.responses.UserInfoResponse;
import com.nullware.ms_users.entity.User;
import com.nullware.ms_users.exceptions.UserAlreadyExistsException;
import com.nullware.ms_users.exceptions.UserNotFoundException;
import com.nullware.ms_users.mappers.UserMapper;
import com.nullware.ms_users.repository.UserRepository;
import com.nullware.ms_users.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Transactional
    public GenericResponse registerUser(RegisterRequestDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.email()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User newUser = UserMapper.toUser(registerDTO);
        LocalDate now = LocalDate.now();
        newUser.setCreatedAt(now);

        userRepository.save(newUser);

        return new GenericResponse("User registered successfully");
    }

    @Override
    public UserInfoResponse getUserInfoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserMapper.toUserInfoResponse(user);
    }

    @Override
    public GenericResponse getSubscriptionStatus(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!Objects.equals(user.getSubscriptionPlanName(), "No active subscription")) {
            return new GenericResponse("User has an active subscription");
        }
        return new GenericResponse("User has no active subscription");
    }
}
