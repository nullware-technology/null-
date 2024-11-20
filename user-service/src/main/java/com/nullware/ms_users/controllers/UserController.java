package com.nullware.ms_users.controllers;

import com.nullware.ms_users.dtos.requests.RegisterRequestDTO;
import com.nullware.ms_users.dtos.responses.GenericResponseDTO;
import com.nullware.ms_users.dtos.responses.UserInfoResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface UserController {

    @PostMapping("/register")
    ResponseEntity<GenericResponseDTO> userRegister(@RequestBody @Valid RegisterRequestDTO registerRequestDTO);

    @GetMapping("/info/{email}")
    ResponseEntity<UserInfoResponseDTO> getUserInfoByEmail(@PathVariable @Valid @Email String email);

    @GetMapping("/{email}/subscription-status")
    ResponseEntity<GenericResponseDTO> getSubscriptionStatus(@PathVariable @Valid @Email String email);
}
