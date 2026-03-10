package dev.danilo.fintrack.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.YearMonth;

public record ExpenseRequest(
    @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
    @NotBlank String category,
    String description,
    YearMonth yearMonth) {}
