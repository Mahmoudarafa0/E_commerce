package com.mahmoud.E_commerce.service.impl;

import com.mahmoud.E_commerce.dto.AuthResponse;
import com.mahmoud.E_commerce.dto.LoginRequest;
import com.mahmoud.E_commerce.dto.RegisterRequest;
import com.mahmoud.E_commerce.entity.User;
import com.mahmoud.E_commerce.entity.enums.Role;
import com.mahmoud.E_commerce.repository.UserRepository;
import com.mahmoud.E_commerce.security.CustomUserDetails;
import com.mahmoud.E_commerce.security.JwtUtil;
import com.mahmoud.E_commerce.service.AuthService;
import com.mahmoud.E_commerce.service.CartService;
import com.mahmoud.E_commerce.service.EmailService;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final CartService cartService;


    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EntityExistsException("Email already exists");
        }
        var user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .confirmationCode(emailService.generateConfirmationCode())
                .emailConfirmation(false)
                .build();

        emailService.sendConfirmationCode(user);
        userRepository.save(user);
        cartService.createCartForUser(user.getId());

        var token = jwtUtil.generateToken(new CustomUserDetails(user));
        return AuthResponse.builder()
                .token(token)
                .expires_in(jwtUtil.extractClaim(token, Claims::getExpiration).getTime())
                .build();
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, jwtUtil.extractClaim(token, Claims::getExpiration).getTime());
    }
}
