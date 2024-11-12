package com.nullware.ms_auth.controllers.impl;

import com.nullware.ms_auth.controllers.AuthController;
import com.nullware.ms_auth.dtos.requests.ForgotPasswordDTO;
import com.nullware.ms_auth.dtos.requests.LoginDTO;
import com.nullware.ms_auth.dtos.requests.RefreshTokenDTO;
import com.nullware.ms_auth.dtos.requests.ResetPasswordDTO;
import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import com.nullware.ms_auth.dtos.responses.ResetPasswordResponseDTO;
import com.nullware.ms_auth.dtos.responses.TokenResponseDTO;
import com.nullware.ms_auth.security.TokenService;
import com.nullware.ms_auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        TokenResponseDTO response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
        String refreshToken = refreshTokenDTO.refreshToken();
        log.info("Received request to refresh access token: {}", refreshToken);

        TokenResponseDTO tokenResponseDTO = tokenService.refreshAccessToken(refreshTokenDTO.refreshToken());

        if (tokenResponseDTO.accessToken() == null) {
            log.error("Failed to refresh access token: {}", refreshToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("Refresh token is valid, generating new access token");

        log.info("New access token generated successfully");
        return ResponseEntity.ok(tokenResponseDTO);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> forgotPassword(@RequestBody @Valid ForgotPasswordDTO forgotPasswordDTO) {
        GenericResponseDTO response = authService.forgotPassword(forgotPasswordDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResetPasswordResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) {
        ResetPasswordResponseDTO response = authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok(response);
    }
}
