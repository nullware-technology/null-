package com.nullware.ms_auth.controllers.impl;

import com.nullware.ms_auth.controllers.HealthzController;
import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthzControllerImpl implements HealthzController {

    @Override
    public ResponseEntity<GenericResponseDTO> healthz() {
        return ResponseEntity.ok(new GenericResponseDTO("OK"));
    }

    @Override
    public ResponseEntity<GenericResponseDTO> authHealthz() {
        return ResponseEntity.ok(new GenericResponseDTO("User Authenticated"));
    }
}
