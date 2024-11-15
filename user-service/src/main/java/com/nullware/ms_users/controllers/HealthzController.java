package com.nullware.ms_users.controllers;

import com.nullware.ms_users.dtos.responses.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/healthz")
public interface HealthzController {

    @GetMapping()
    ResponseEntity<GenericResponse> healthz();
}
