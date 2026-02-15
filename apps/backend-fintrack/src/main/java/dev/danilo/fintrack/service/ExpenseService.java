package dev.danilo.fintrack.service;

import dev.danilo.fintrack.dto.request.ExpenseRequest;
import dev.danilo.fintrack.dto.response.ExpenseResponse;
import dev.danilo.fintrack.entity.Expense;
import dev.danilo.fintrack.entity.User;
import dev.danilo.fintrack.exception.ExpenseNotFoundException;
import dev.danilo.fintrack.exception.InvalidMonthFormatException;
import dev.danilo.fintrack.repository.ExpenseRepository;
import dev.danilo.fintrack.util.FindAuthenticatedUser;
import dev.danilo.fintrack.util.mapper.ExpenseMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final ExpenseMapper expenseMapper;
  private final FindAuthenticatedUser findAuthenticatedUser;

  public ExpenseService(
      ExpenseRepository expenseRepository,
      ExpenseMapper expenseMapper,
      FindAuthenticatedUser findAuthenticatedUser) {
    this.expenseRepository = expenseRepository;
    this.expenseMapper = expenseMapper;
    this.findAuthenticatedUser = findAuthenticatedUser;
  }

  @Transactional
  public ExpenseResponse createExpense(ExpenseRequest expenseRequest) {
    User user = findAuthenticatedUser.getAuthenticatedUser();
    Expense savedExpense =
        expenseRepository.save(expenseMapper.toExpenseEntity(expenseRequest, user));

    return expenseMapper.toExpenseResponse(savedExpense);
  }

  public List<ExpenseResponse> findExpenseByMonthAndCurrentUser(String month) {
    User user = findAuthenticatedUser.getAuthenticatedUser();
    YearMonth yearMonth;

    try {
      yearMonth = YearMonth.parse(month);
    } catch (DateTimeParseException ex) {
      throw new InvalidMonthFormatException(month);
    }

    LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
    LocalDateTime end = start.plusMonths(1);

    List<Expense> expenses =
        expenseRepository.findByUserAndCreatedAtBetweenOrderByCreatedAtDesc(user, start, end);

    return expenses.stream().map(expenseMapper::toExpenseResponse).toList();
  }

  public List<ExpenseResponse> findExpensesByCurrentUser() {
    User user = findAuthenticatedUser.getAuthenticatedUser();

    return expenseRepository.findByUserOrderByCreatedAtDesc(user).stream()
        .map(expenseMapper::toExpenseResponse)
        .toList();
  }

  public ExpenseResponse findExpenseByIdAndCurrentUser(Long expenseId) {
    User user = findAuthenticatedUser.getAuthenticatedUser();

    return expenseMapper.toExpenseResponse(
        expenseRepository
            .findByIdAndUser(user, expenseId)
            .orElseThrow(ExpenseNotFoundException::new));
  }

  @Transactional
  public ExpenseResponse updateExpense(Long expenseId, @Valid ExpenseRequest expenseRequest) {
    User user = findAuthenticatedUser.getAuthenticatedUser();
    Expense expense =
        expenseRepository
            .findByIdAndUser(user, expenseId)
            .orElseThrow(ExpenseNotFoundException::new);

    expense.setAmount(expenseRequest.amount());
    expense.setCategory(expenseRequest.category());
    expense.setDescription(expenseRequest.description());

    Expense savedExpense = expenseRepository.save(expense);

    return expenseMapper.toExpenseResponse(savedExpense);
  }

  @Transactional
  public void deleteExpenseById(Long expenseId) {
    User user = findAuthenticatedUser.getAuthenticatedUser();
    Expense expense =
        expenseRepository
            .findByIdAndUser(user, expenseId)
            .orElseThrow(ExpenseNotFoundException::new);
    expenseRepository.delete(expense);
  }
}
