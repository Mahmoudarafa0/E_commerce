package com.mahmoud.E_commerce.service;

import com.mahmoud.E_commerce.dto.AuthResponse;
import com.mahmoud.E_commerce.dto.LoginRequest;
import com.mahmoud.E_commerce.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse authenticate(LoginRequest loginRequest);
}
