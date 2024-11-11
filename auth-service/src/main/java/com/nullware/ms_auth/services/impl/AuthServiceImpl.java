package com.nullware.ms_auth.services.impl;

import com.nullware.ms_auth.dtos.requests.ForgotPasswordDTO;
import com.nullware.ms_auth.dtos.requests.LoginDTO;
import com.nullware.ms_auth.dtos.requests.RefreshTokenDTO;
import com.nullware.ms_auth.dtos.requests.ResetPasswordDTO;
import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import com.nullware.ms_auth.dtos.responses.LoginResponseDTO;
import com.nullware.ms_auth.dtos.responses.RefreshTokenResponseDTO;
import com.nullware.ms_auth.dtos.responses.ResetPasswordResponseDTO;
import com.nullware.ms_auth.exceptions.*;
import com.nullware.ms_auth.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) throws InvalidCredentialsException, AccountLockedException {
        return null;
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws InvalidTokenException {
        return null;
    }

    @Override
    public GenericResponseDTO logout() throws UserNotAuthenticatedException {
        return null;
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
