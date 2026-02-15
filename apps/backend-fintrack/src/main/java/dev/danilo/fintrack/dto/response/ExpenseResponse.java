package dev.danilo.fintrack.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseResponse(
    Long id, BigDecimal amount, String category, String description, LocalDateTime created_at) {}
