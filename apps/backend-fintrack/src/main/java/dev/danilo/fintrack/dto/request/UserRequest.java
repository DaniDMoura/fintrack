package dev.danilo.fintrack.dto.request;

import dev.danilo.fintrack.entity.Income;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @Email @NotBlank String email, @NotBlank String password, @NotNull Income income) {}
