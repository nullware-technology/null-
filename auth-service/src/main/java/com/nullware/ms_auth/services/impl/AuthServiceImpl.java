package com.nullware.ms_auth.services.impl;

import com.nullware.ms_auth.dtos.requests.ForgotPasswordDTO;
import com.nullware.ms_auth.dtos.requests.LoginDTO;
import com.nullware.ms_auth.dtos.requests.ResetPasswordDTO;
import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import com.nullware.ms_auth.dtos.responses.ResetPasswordResponseDTO;
import com.nullware.ms_auth.dtos.responses.TokenResponseDTO;
import com.nullware.ms_auth.entity.User;
import com.nullware.ms_auth.exceptions.*;
import com.nullware.ms_auth.producers.UserProducer;
import com.nullware.ms_auth.repository.UserRepository;
import com.nullware.ms_auth.security.TokenService;
import com.nullware.ms_auth.services.AuthService;
import com.nullware.ms_auth.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    final UserRepository userRepo;
    final UserProducer userProducer;
    final PasswordEncoder passwordEncoder;
    final TokenService tokenService;

    @Override
    public TokenResponseDTO login(LoginDTO loginDTO) throws InvalidCredentialsException, AccountLockedException {
        log.info("Login attempt for email: {}", loginDTO.email());

        User user = this.userRepo.findByEmail(loginDTO.email()).orElseThrow(() -> new UsernameNotFoundException("User not found."));

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
        log.info("Recovering password for user with email {}", forgotPasswordDTO.email());
        User user = this.userRepo.findByEmail(forgotPasswordDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user != null) {
            log.info("Generating new password for user {}", user.getEmail());
            var oldPassword = user.getPassword();
            var newPassword = PasswordGenerator.generateRandomPassword(14);

            user.setPassword(passwordEncoder.encode(newPassword));
            this.userRepo.save(user);

            log.info("Password updated successfully for user {}", user.getEmail());

            // TODO: Send password recovery email
            this.userProducer.publishRecoverPasswordMessageEmail(user, newPassword);

            log.info("Recover password message sent to user {}", user.getEmail());
            this.schedulePasswordRevert(user, oldPassword, newPassword);

            log.info("Scheduled password revert for user {}", user.getEmail());
            return new GenericResponseDTO("Password reset successfully");
        }
        return new GenericResponseDTO("User not found");
    }

    @Override
    public ResetPasswordResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws InvalidTokenException, PasswordComplexityException {
        log.info("Changing password for user with email {}", resetPasswordDTO.email());
        User user = this.userRepo.findByEmail(resetPasswordDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(resetPasswordDTO.oldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.newPassword()));
            this.userRepo.save(user);
            log.info("Password changed successfully for user {}", user.getEmail());
            return new ResetPasswordResponseDTO("Password changed successfully", true);
        } else {
            log.warn("Invalid old password for user {}", user.getEmail());
            return new ResetPasswordResponseDTO("Invalid old password", false);
        }
    }

    private void schedulePasswordRevert(User user, String oldPassword, String newPassword) {
        log.info("Scheduling password revert for user {}", user.getEmail());
        long expirationTimeMillis = 20; // 20 minutes expiration time

        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            log.info("Reverting password for user {}", user.getEmail());
            User currentUser = userRepo.findById(user.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (passwordEncoder.matches(newPassword, currentUser.getPassword())) {
                log.info("Reverting password to old password for user {}", user.getEmail());
                currentUser.setPassword(oldPassword);
                userRepo.save(currentUser);
                log.info("Password reverted successfully for user {}", user.getEmail());
            }
        }, expirationTimeMillis, TimeUnit.MINUTES);
    }
}
