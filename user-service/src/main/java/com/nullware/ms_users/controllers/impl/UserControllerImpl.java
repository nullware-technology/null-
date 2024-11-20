package com.nullware.ms_users.controllers.impl;

import com.nullware.ms_users.controllers.UserController;
import com.nullware.ms_users.dtos.requests.RegisterRequestDTO;
import com.nullware.ms_users.dtos.responses.GenericResponseDTO;
import com.nullware.ms_users.dtos.responses.UserInfoResponseDTO;
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
    public ResponseEntity<GenericResponseDTO> userRegister(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        GenericResponseDTO registerResponseDTO = userService.registerUser(registerRequestDTO);

        return ResponseEntity.ok(registerResponseDTO);
    }

    @Override
    public ResponseEntity<UserInfoResponseDTO> getUserInfoByEmail(@PathVariable @Valid @Email String email) {
        UserInfoResponseDTO userInfoResponseDTO = userService.getUserInfoByEmail(email);

        return ResponseEntity.ok(userInfoResponseDTO);
    }

    @Override
    public ResponseEntity<GenericResponseDTO> getSubscriptionStatus(@PathVariable @Valid @Email String email) {
        GenericResponseDTO subscriptionStatus = userService.getSubscriptionStatus(email);
        return ResponseEntity.ok(subscriptionStatus);
    }
}
