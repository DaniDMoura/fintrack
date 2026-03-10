package dev.danilo.fintrack.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

public record ExpenseResponse(
    Long id,
    BigDecimal amount,
    String category,
    String description,
    YearMonth yearMonth,
    LocalDateTime created_at) {}
