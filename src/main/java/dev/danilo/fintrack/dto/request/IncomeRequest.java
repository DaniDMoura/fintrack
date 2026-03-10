package dev.danilo.fintrack.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.YearMonth;

public record IncomeRequest(
    @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
    String description,
    YearMonth yearMonth) {}
