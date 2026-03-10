package dev.danilo.fintrack.dto.response;

import java.math.BigDecimal;

public record IncomeResponse(BigDecimal amount, String description) {}
