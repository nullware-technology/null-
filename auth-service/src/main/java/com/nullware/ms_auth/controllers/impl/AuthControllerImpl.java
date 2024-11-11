package com.nullware.ms_auth.controllers;

import com.nullware.ms_auth.dtos.requests.ForgotPasswordDTO;
import com.nullware.ms_auth.dtos.requests.LoginDTO;
import com.nullware.ms_auth.dtos.requests.RefreshTokenDTO;
import com.nullware.ms_auth.dtos.requests.ResetPasswordDTO;
import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import com.nullware.ms_auth.dtos.responses.LoginResponseDTO;
import com.nullware.ms_auth.dtos.responses.RefreshTokenResponseDTO;
import com.nullware.ms_auth.dtos.responses.ResetPasswordResponseDTO;
import com.nullware.ms_auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginResponseDTO response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
        RefreshTokenResponseDTO response = authService.refreshToken(refreshTokenDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> logout() {
        GenericResponseDTO response = authService.logout();
        return ResponseEntity.ok(response);
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
