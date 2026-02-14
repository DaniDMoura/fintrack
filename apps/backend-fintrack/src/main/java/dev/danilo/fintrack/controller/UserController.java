package dev.danilo.fintrack.controller;

import dev.danilo.fintrack.dto.response.UserResponse;
import dev.danilo.fintrack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/me")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> findCurrentUser() {
        return ResponseEntity.ok(
                userService.findCurrentUser()
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser() {
        userService.deleteCurrentUser();

        return ResponseEntity.noContent().build();
    }
}
