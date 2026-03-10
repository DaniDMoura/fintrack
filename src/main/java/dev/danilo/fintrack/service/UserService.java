package dev.danilo.fintrack.service;

import dev.danilo.fintrack.dto.response.UserResponse;
import dev.danilo.fintrack.exception.UserNotFoundException;
import dev.danilo.fintrack.repository.UserRepository;
import dev.danilo.fintrack.util.FindAuthenticatedUser;
import dev.danilo.fintrack.util.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final FindAuthenticatedUser findAuthenticatedUser;

  public UserService(
      UserRepository userRepository,
      UserMapper userMapper,
      FindAuthenticatedUser findAuthenticatedUser) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.findAuthenticatedUser = findAuthenticatedUser;
  }

  @Override
  public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  public UserResponse findCurrentUser() {
    return userMapper.toUserResponse(findAuthenticatedUser.getAuthenticatedUser());
  }

  @Transactional
  public void deleteCurrentUser() {
    userRepository.delete(findAuthenticatedUser.getAuthenticatedUser());
  }
}
