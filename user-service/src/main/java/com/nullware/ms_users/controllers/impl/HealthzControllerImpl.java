package com.nullware.ms_users.controllers.impl;

import com.nullware.ms_users.controllers.HealthzController;
import com.nullware.ms_users.dtos.responses.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthzControllerImpl implements HealthzController {

    @Override
    public ResponseEntity<GenericResponse> healthz() {
        return ResponseEntity.ok(new GenericResponse("OK"));
    }
}
