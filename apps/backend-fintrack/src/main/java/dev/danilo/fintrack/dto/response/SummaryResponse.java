package dev.danilo.fintrack.dto.response;

import java.math.BigDecimal;
import java.time.YearMonth;

public record SummaryResponse(
    YearMonth yearMonth, BigDecimal income, BigDecimal totalExpenses, BigDecimal balance) {}
