package dev.danilo.fintrack.dto.response;

import java.math.BigDecimal;
import java.time.YearMonth;

public record ExpenseSummaryResponse(YearMonth yearMonth, BigDecimal totalAmount) {}
