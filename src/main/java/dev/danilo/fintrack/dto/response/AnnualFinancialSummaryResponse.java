package dev.danilo.fintrack.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record AnnualFinancialSummaryResponse(
    int year,
    BigDecimal totalIncomeAmount,
    BigDecimal totalExpenseAmount,
    BigDecimal annualNetResult,
    BigDecimal annualSavingsRate,
    BigDecimal averageMonthlyIncome,
    BigDecimal averageMonthlyExpense,
    BigDecimal highestMonthlyIncome,
    BigDecimal lowestMonthlyIncome,
    BigDecimal highestMonthlyExpense,
    BigDecimal lowestMonthlyExpense,
    List<MonthlyFinancialSummaryResponse> months) {}
