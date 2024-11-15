package com.nullware.ms_users.services;

import com.nullware.ms_users.dtos.requests.RegisterRequestDTO;
import com.nullware.ms_users.dtos.responses.GenericResponse;
import com.nullware.ms_users.dtos.responses.UserInfoResponse;

public interface UserService {
    GenericResponse registerUser(RegisterRequestDTO registerDTO);
    UserInfoResponse getUserInfoByEmail(String email);
    GenericResponse getSubscriptionStatus(String email);
}
