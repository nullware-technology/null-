package com.nullware.ms_auth.services.impl;

import com.nullware.ms_auth.dtos.requests.ForgotPasswordDTO;
import com.nullware.ms_auth.dtos.requests.LoginDTO;
import com.nullware.ms_auth.dtos.requests.ResetPasswordDTO;
import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import com.nullware.ms_auth.dtos.responses.TokenResponseDTO;
import com.nullware.ms_auth.dtos.responses.ResetPasswordResponseDTO;
import com.nullware.ms_auth.entity.User;
import com.nullware.ms_auth.exceptions.*;
import com.nullware.ms_auth.repository.UserRepository;
import com.nullware.ms_auth.security.TokenService;
import com.nullware.ms_auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    final UserRepository userRepo;
    final PasswordEncoder passwordEncoder;
    final TokenService tokenService;

    @Override
    public TokenResponseDTO login(LoginDTO loginDTO) throws InvalidCredentialsException, AccountLockedException {
        log.info("Login attempt for email: {}", loginDTO.email());

        User user = this.userRepo.findByEmail(loginDTO.email()).orElseThrow(() -> new InvalidCredentialsException("User not found."));

        if (!passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            log.warn("Invalid password for user {}", user.getEmail());
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        log.info("Login successful for user {}", user.getEmail());
        TokenResponseDTO tokenResponseDTO = this.tokenService.generateTokens(user.getEmail());

        log.info("Token response: {}", tokenResponseDTO);
        return tokenResponseDTO;
    }

    @Override
    public GenericResponseDTO forgotPassword(ForgotPasswordDTO forgotPasswordDTO) throws EmailNotFoundException, EmailSendingException {
        return null;
    }

    @Override
    public ResetPasswordResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws InvalidTokenException, PasswordComplexityException {
        return null;
    }
}
