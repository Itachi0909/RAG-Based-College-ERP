package com.rcet.backend.controller.auth;

import com.rcet.backend.dto.request.LoginRequest;
import com.rcet.backend.dto.request.RegisterAdminRequest;
import com.rcet.backend.dto.response.LoginResponse;
import com.rcet.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ==========================
    // Login
    // ==========================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    // ==========================
    // Register Admin
    // ==========================

    @PostMapping("/register/admin")
    public ResponseEntity<LoginResponse> registerAdmin(
            @Valid @RequestBody RegisterAdminRequest request) {

        return ResponseEntity.ok(
                authService.registerAdmin(request)
        );
    }
}