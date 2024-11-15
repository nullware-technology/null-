package com.nullware.ms_users.controllers.impl;

import com.nullware.ms_users.controllers.UserController;
import com.nullware.ms_users.dtos.requests.RegisterRequestDTO;
import com.nullware.ms_users.dtos.responses.GenericResponse;
import com.nullware.ms_users.dtos.responses.UserInfoResponse;
import com.nullware.ms_users.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    final UserService userService;

    @Override
    public ResponseEntity<GenericResponse> userRegister(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        GenericResponse registerResponseDTO = userService.registerUser(registerRequestDTO);

        return ResponseEntity.ok(registerResponseDTO);
    }

    @Override
    public ResponseEntity<UserInfoResponse> getUserInfoByEmail(@PathVariable @Valid @Email String email) {
        UserInfoResponse userInfoResponse = userService.getUserInfoByEmail(email);

        return ResponseEntity.ok(userInfoResponse);
    }

    @Override
    public ResponseEntity<GenericResponse> getSubscriptionStatus(@PathVariable @Valid @Email String email) {
        GenericResponse subscriptionStatus = userService.getSubscriptionStatus(email);
        return ResponseEntity.ok(subscriptionStatus);
    }
}
