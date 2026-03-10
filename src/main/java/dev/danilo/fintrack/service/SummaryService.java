package dev.danilo.fintrack.service;

import dev.danilo.fintrack.dto.response.AnnualFinancialSummaryResponse;
import dev.danilo.fintrack.dto.response.ExpenseSummaryResponse;
import dev.danilo.fintrack.dto.response.MonthlyFinancialSummaryResponse;
import dev.danilo.fintrack.entity.Income;
import dev.danilo.fintrack.entity.User;
import dev.danilo.fintrack.repository.ExpenseRepository;
import dev.danilo.fintrack.repository.IncomeRepository;
import dev.danilo.fintrack.util.FindAuthenticatedUser;
import dev.danilo.fintrack.util.YearMonthParser;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SummaryService {

  private final FindAuthenticatedUser findAuthenticatedUser;
  private final ExpenseRepository expenseRepository;
  private final IncomeRepository incomeRepository;
  private final YearMonthParser yearMonthParser;

  public SummaryService(
      FindAuthenticatedUser findAuthenticatedUser,
      ExpenseRepository expenseRepository,
      IncomeRepository incomeRepository,
      YearMonthParser yearMonthParser) {
    this.findAuthenticatedUser = findAuthenticatedUser;
    this.expenseRepository = expenseRepository;
    this.incomeRepository = incomeRepository;
    this.yearMonthParser = yearMonthParser;
  }

  public MonthlyFinancialSummaryResponse getMonthlyFinancialSummary(String month) {
    User user = findAuthenticatedUser.getAuthenticatedUser();
    YearMonth yearMonth = yearMonthParser.parseMonthOrNow(month);

    BigDecimal totalExpenses = expenseRepository.sumTotalExpenseByUserAndYearMonth(user, yearMonth);

    BigDecimal totalIncome =
        incomeRepository
            .findFirstByUserAndYearMonthLessThanEqualOrderByYearMonthDescCreatedAtDesc(
                user, yearMonth)
            .map(Income::getAmount)
            .orElse(BigDecimal.ZERO);

    BigDecimal netResult = totalIncome.subtract(totalExpenses);

    BigDecimal savingsRate = calculateSavingsRate(netResult, totalIncome);

    return new MonthlyFinancialSummaryResponse(
        yearMonth, totalIncome, totalExpenses, netResult, savingsRate);
  }

  private BigDecimal calculateSavingsRate(BigDecimal netResult, BigDecimal totalIncome) {
    if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }

    return netResult.divide(totalIncome, 6, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);
  }

  public AnnualFinancialSummaryResponse getAnnualFinancialSummary(Integer year) {
    User user = findAuthenticatedUser.getAuthenticatedUser();

    if (year == null) {
      year = Year.now().getValue();
    }

    YearMonth startYearMonth = YearMonth.of(year, Month.JANUARY);
    YearMonth endYearMonth = YearMonth.of(year, Month.DECEMBER);

    List<Income> annualIncomes =
        incomeRepository.findAllByUserAndYear(user, startYearMonth, endYearMonth);

    List<ExpenseSummaryResponse> annualExpenses =
        expenseRepository.sumExpensesByUserAndYear(user, startYearMonth, endYearMonth);

    List<MonthlyFinancialSummaryResponse> months =
        generateAnnualMonths(year, annualIncomes, annualExpenses);

    BigDecimal totalIncomeAmount =
        months.stream()
            .map(MonthlyFinancialSummaryResponse::totalIncome)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal totalExpenseAmount =
        months.stream()
            .map(MonthlyFinancialSummaryResponse::totalExpense)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal annualNetResult = totalIncomeAmount.subtract(totalExpenseAmount);

    BigDecimal annualSavingsRate = calculateSavingsRate(annualNetResult, totalIncomeAmount);

    BigDecimal averageTotalIncomeAmount =
        totalIncomeAmount.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

    BigDecimal averageTotalExpenseAmount =
        totalExpenseAmount.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

    BigDecimal maxIncomeAmount =
        months.stream()
            .map(MonthlyFinancialSummaryResponse::totalIncome)
            .max(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);

    BigDecimal maxExpenseAmount =
        months.stream()
            .map(MonthlyFinancialSummaryResponse::totalExpense)
            .max(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);

    BigDecimal minIncomeAmount =
        months.stream()
            .map(MonthlyFinancialSummaryResponse::totalIncome)
            .min(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);

    BigDecimal minExpenseAmount =
        months.stream()
            .map(MonthlyFinancialSummaryResponse::totalExpense)
            .min(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);

    return new AnnualFinancialSummaryResponse(
        year,
        totalIncomeAmount,
        totalExpenseAmount,
        annualNetResult,
        annualSavingsRate,
        averageTotalIncomeAmount,
        averageTotalExpenseAmount,
        maxIncomeAmount,
        maxExpenseAmount,
        minIncomeAmount,
        minExpenseAmount,
        months);
  }

  private List<MonthlyFinancialSummaryResponse> generateAnnualMonths(
      Integer year, List<Income> annualIncomes, List<ExpenseSummaryResponse> annualExpenses) {

    List<MonthlyFinancialSummaryResponse> months = new ArrayList<>(12);

    Map<YearMonth, BigDecimal> expenseByMonth =
        annualExpenses.stream()
            .collect(
                Collectors.toMap(
                    ExpenseSummaryResponse::yearMonth,
                    ExpenseSummaryResponse::totalAmount,
                    BigDecimal::add));

    BigDecimal currentIncome = BigDecimal.ZERO;
    int index = 0;

    for (int month = 1; month <= 12; month++) {

      YearMonth yearMonth = YearMonth.of(year, month);

      while (index < annualIncomes.size()
          && !annualIncomes.get(index).getYearMonth().isAfter(yearMonth)) {

        currentIncome = annualIncomes.get(index).getAmount();
        index++;
      }

      BigDecimal totalIncome = currentIncome;
      BigDecimal totalExpense = expenseByMonth.getOrDefault(yearMonth, BigDecimal.ZERO);
      BigDecimal netResult = totalIncome.subtract(totalExpense);
      BigDecimal savingsRate = calculateSavingsRate(netResult, totalIncome);

      months.add(
          new MonthlyFinancialSummaryResponse(
              yearMonth, totalIncome, totalExpense, netResult, savingsRate));
    }

    return months;
  }
}
