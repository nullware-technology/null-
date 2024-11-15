package com.nullware.ms_users.controllers;

import com.nullware.ms_users.dtos.requests.RegisterRequestDTO;
import com.nullware.ms_users.dtos.responses.GenericResponse;
import com.nullware.ms_users.dtos.responses.UserInfoResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface UserController {

    @PostMapping("/register")
    ResponseEntity<GenericResponse> userRegister(@RequestBody @Valid RegisterRequestDTO registerRequestDTO);

    @GetMapping("/info/{email}")
    ResponseEntity<UserInfoResponse> getUserInfoByEmail(@PathVariable @Valid @Email String email);

    @GetMapping("/{email}/subscription-status")
    ResponseEntity<GenericResponse> getSubscriptionStatus(@PathVariable @Valid @Email String email);
}
