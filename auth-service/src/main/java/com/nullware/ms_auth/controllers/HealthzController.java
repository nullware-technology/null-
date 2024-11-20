package com.nullware.ms_auth.controllers;

import com.nullware.ms_auth.dtos.responses.GenericResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for checking the health of the service and the authentication service.
 * It contains endpoints to check the status of the service and the authentication service.
 */
@RequestMapping("/healthz")
public interface HealthzController {

    /**
     * Checks the health of the service.
     * <p>
     * This endpoint is used to verify if the service is running correctly.
     * It returns a response with the service status (200 OK if the service is running, 500 if there is an error).
     * </p>
     *
     * @return ResponseEntity with the service status.
     */
    @Operation(
            summary = "Checks the health of the service",
            description = "Route used to check the status of the service's operation.",
            tags = { "Health Check" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service is functioning correctly"),
            @ApiResponse(responseCode = "500", description = "Service error")
    })
    @GetMapping()
    ResponseEntity<GenericResponseDTO> healthz();

    /**
     * Checks the health of the authentication service.
     * <p>
     * This endpoint is used to verify if the authentication service is able to authenticate users and generate sessions correctly.
     * It returns a response with the authentication service status (200 OK if the service is working, 401 if the credentials are incorrect, 500 if there is an error in the service).
     * </p>
     *
     * @return ResponseEntity with the authentication service status.
     */
    @Operation(
            summary = "Checks the health of the authentication service",
            description = "Route used to check if the authentication service can authenticate users and generate sessions correctly.",
            tags = { "Health Check", "Auth" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication service is working correctly and able to generate sessions"),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials, unable to generate session"),
            @ApiResponse(responseCode = "500", description = "Authentication service error, unable to generate session")
    })
    @GetMapping("/auth")
    ResponseEntity<GenericResponseDTO> authHealthz();
}
