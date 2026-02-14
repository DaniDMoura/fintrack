package dev.danilo.fintrack.util.mapper;

import dev.danilo.fintrack.dto.request.UserRequest;
import dev.danilo.fintrack.dto.response.UserResponse;
import dev.danilo.fintrack.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getIncome(),
                user.getExpenses()
        );
    }

    public User toUserEntity(UserRequest userRequest) {
        return new User(
                userRequest.income(),
                userRequest.email(),
                passwordEncoder.encode(userRequest.password())
        );
    }
}
