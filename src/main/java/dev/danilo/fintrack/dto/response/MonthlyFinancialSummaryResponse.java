package dev.danilo.fintrack.dto.response;

import java.math.BigDecimal;
import java.time.YearMonth;

public record MonthlyFinancialSummaryResponse(
    YearMonth period,
    BigDecimal totalIncome,
    BigDecimal totalExpense,
    BigDecimal netResult,
    BigDecimal savingsRate) {}
