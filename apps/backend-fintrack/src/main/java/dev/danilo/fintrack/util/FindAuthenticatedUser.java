package dev.danilo.fintrack.util;

import dev.danilo.fintrack.entity.User;
import dev.danilo.fintrack.exception.AuthenticationRequiredException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FindAuthenticatedUser {
  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null
        || !authentication.isAuthenticated()
        || authentication.getPrincipal() instanceof AnonymousAuthenticationToken) {
      throw new AuthenticationRequiredException();
    }

    return (User) authentication.getPrincipal();
  }
}
