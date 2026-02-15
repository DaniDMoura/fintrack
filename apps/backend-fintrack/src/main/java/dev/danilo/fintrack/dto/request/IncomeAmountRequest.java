package dev.danilo.fintrack.dto.request;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public record IncomeAmountRequest(
    @DecimalMin(value = "0.00", inclusive = true) BigDecimal amount) {}
