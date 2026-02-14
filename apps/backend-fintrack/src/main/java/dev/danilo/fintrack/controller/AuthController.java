package dev.danilo.fintrack.controller;

import dev.danilo.fintrack.dto.request.LoginRequest;
import dev.danilo.fintrack.dto.request.UserRequest;
import dev.danilo.fintrack.dto.response.LoginResponse;
import dev.danilo.fintrack.dto.response.UserResponse;
import dev.danilo.fintrack.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
          authService.registerUser(userRequest)
        );
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authService.login(loginRequest)
        );
    }
}
