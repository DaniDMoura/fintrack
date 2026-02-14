package dev.danilo.fintrack.dto.request;

import dev.danilo.fintrack.entity.User;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IncomeRequest(

        @NotNull
        User user,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        String description
) {
}
