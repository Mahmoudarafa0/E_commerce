package com.mahmoud.E_commerce.controller;

import com.mahmoud.E_commerce.dto.AuthResponse;
import com.mahmoud.E_commerce.dto.LoginRequest;
import com.mahmoud.E_commerce.dto.RegisterRequest;
import com.mahmoud.E_commerce.service.AuthService;
import com.mahmoud.E_commerce.utils.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication operation")
@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registration operation")
    @PostMapping(path = "/register")
    public ResponseEntity<GlobalResponse<AuthResponse>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);
        GlobalResponse<AuthResponse> response = new GlobalResponse<>(authResponse);
        response.setMessage("User registered successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Login operation")
    @PostMapping(path = "/login")
    public ResponseEntity<GlobalResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.authenticate(loginRequest);
        GlobalResponse<AuthResponse> response = new GlobalResponse<>(authResponse);
        response.setMessage("User logged in successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
