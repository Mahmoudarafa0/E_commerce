package com.mahmoud.E_commerce.controller;

import com.mahmoud.E_commerce.dto.ChangePasswordRequest;
import com.mahmoud.E_commerce.service.UserService;
import com.mahmoud.E_commerce.utils.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users")
@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "User can change his password")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping(path = "/change-password")
    public ResponseEntity<GlobalResponse<?>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        GlobalResponse<?> response = new GlobalResponse<>(null);
        response.setMessage("Password changed successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User can activate his account via confirmation code sent on email")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping(path = "/confirm-email")
    public ResponseEntity<GlobalResponse<?>> confirmEmail(@RequestBody String confirmationCode) {
        userService.confirmEmail(confirmationCode);
        GlobalResponse<?> response = new GlobalResponse<>(null);
        response.setMessage("Email confirmed successfully");
        return ResponseEntity.ok(response);
    }
}
