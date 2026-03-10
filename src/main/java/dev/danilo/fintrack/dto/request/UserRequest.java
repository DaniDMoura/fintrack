package dev.danilo.fintrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @Email @NotBlank String email, @NotBlank String password, @NotNull IncomeRequest income) {}
