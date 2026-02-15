package dev.danilo.fintrack.service;

import dev.danilo.fintrack.dto.response.SummaryResponse;
import dev.danilo.fintrack.entity.Income;
import dev.danilo.fintrack.entity.User;
import dev.danilo.fintrack.exception.InvalidMonthFormatException;
import dev.danilo.fintrack.repository.ExpenseRepository;
import dev.danilo.fintrack.repository.IncomeRepository;
import dev.danilo.fintrack.util.FindAuthenticatedUser;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Service;

@Service
public class SummaryService {

  private final FindAuthenticatedUser findAuthenticatedUser;
  private final ExpenseRepository expenseRepository;
  private final IncomeRepository incomeRepository;

  public SummaryService(
      FindAuthenticatedUser findAuthenticatedUser,
      ExpenseRepository expenseRepository,
      IncomeRepository incomeRepository) {
    this.findAuthenticatedUser = findAuthenticatedUser;
    this.expenseRepository = expenseRepository;
    this.incomeRepository = incomeRepository;
  }

  public SummaryResponse getSummary(String month) {
    User user = findAuthenticatedUser.getAuthenticatedUser();
    YearMonth yearMonth;

    try {
      yearMonth = YearMonth.parse(month);
    } catch (DateTimeParseException ex) {
      throw new InvalidMonthFormatException(month);
    }

    LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
    LocalDateTime end = start.plusMonths(1);

    BigDecimal totalExpenses = expenseRepository.sumByUserAndCreatedAtBetween(user, start, end);

    BigDecimal income =
        incomeRepository.findByUser(user).map(Income::getAmount).orElse(BigDecimal.ZERO);

    BigDecimal balance = income.subtract(totalExpenses != null ? totalExpenses : BigDecimal.ZERO);

    return new SummaryResponse(
        yearMonth, income, totalExpenses != null ? totalExpenses : BigDecimal.ZERO, balance);
  }
}
