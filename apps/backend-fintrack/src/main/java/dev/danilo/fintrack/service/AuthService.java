package dev.danilo.fintrack.service;

import dev.danilo.fintrack.dto.request.LoginRequest;
import dev.danilo.fintrack.dto.request.UserRequest;
import dev.danilo.fintrack.dto.response.LoginResponse;
import dev.danilo.fintrack.dto.response.UserResponse;
import dev.danilo.fintrack.entity.User;
import dev.danilo.fintrack.exception.UserEmailAlreadyExistsException;
import dev.danilo.fintrack.exception.UserNotFoundException;
import dev.danilo.fintrack.repository.UserRepository;
import dev.danilo.fintrack.util.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse registerUser(UserRequest userRequest) {
        if (!isEmailTaken(userRequest.email())) {
            throw new UserEmailAlreadyExistsException(userRequest.email());
        }

        User user = userRepository.save(userMapper.toUserEntity(userRequest));
        return userMapper.toUserResponse(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(UserNotFoundException::new);

        String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return new LoginResponse(jwtToken);
    }

    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
