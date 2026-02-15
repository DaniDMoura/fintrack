package dev.danilo.fintrack.service;

import dev.danilo.fintrack.dto.response.ExpenseResponse;
import dev.danilo.fintrack.dto.response.IncomeResponse;
import dev.danilo.fintrack.dto.response.UserResponse;
import dev.danilo.fintrack.entity.Authority;
import dev.danilo.fintrack.entity.Expense;
import dev.danilo.fintrack.entity.Income;
import dev.danilo.fintrack.entity.User;
import dev.danilo.fintrack.exception.ExpenseNotFoundException;
import dev.danilo.fintrack.exception.IncomeNotFoundException;
import dev.danilo.fintrack.exception.InvalidMonthFormatException;
import dev.danilo.fintrack.exception.UserNotFoundException;
import dev.danilo.fintrack.repository.ExpenseRepository;
import dev.danilo.fintrack.repository.IncomeRepository;
import dev.danilo.fintrack.repository.UserRepository;
import dev.danilo.fintrack.util.mapper.ExpenseMapper;
import dev.danilo.fintrack.util.mapper.IncomeMapper;
import dev.danilo.fintrack.util.mapper.UserMapper;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

  private final UserRepository userRepository;
  private final ExpenseRepository expenseRepository;
  private final IncomeRepository incomeRepository;
  private final UserMapper userMapper;
  private final ExpenseMapper expenseMapper;
  private final IncomeMapper incomeMapper;

  public AdminService(
      UserRepository userRepository,
      ExpenseRepository expenseRepository,
      IncomeRepository incomeRepository,
      UserMapper userMapper,
      ExpenseMapper expenseMapper,
      IncomeMapper incomeMapper) {
    this.userRepository = userRepository;
    this.expenseRepository = expenseRepository;
    this.incomeRepository = incomeRepository;
    this.userMapper = userMapper;
    this.expenseMapper = expenseMapper;
    this.incomeMapper = incomeMapper;
  }

  public List<UserResponse> getAllUsers() {
    return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
  }

  public UserResponse getUserById(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    return userMapper.toUserResponse(user);
  }

  @Transactional
  public void deleteUserById(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    userRepository.delete(user);
  }

  @Transactional
  public UserResponse setUserRoleAdmin(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    user.setAuthority(Authority.ROLE_ADMIN);

    return userMapper.toUserResponse(user);
  }

  public List<ExpenseResponse> getAllExpenses(Long userId, String month) {

    LocalDateTime start = null;
    LocalDateTime end = null;

    if (month != null) {
      try {
        YearMonth yearMonth = YearMonth.parse(month);
        start = yearMonth.atDay(1).atStartOfDay();
        end = start.plusMonths(1);
      } catch (DateTimeParseException ex) {
        throw new InvalidMonthFormatException(month);
      }
    }

    return expenseRepository.findWithFilters(userId, start, end).stream()
        .map(expenseMapper::toExpenseResponse)
        .toList();
  }

  public ExpenseResponse getExpenseById(Long expenseId) {
    Expense expense =
        expenseRepository.findById(expenseId).orElseThrow(ExpenseNotFoundException::new);

    return expenseMapper.toExpenseResponse(expense);
  }

  @Transactional
  public void deleteExpenseById(Long expenseId) {
    Expense expense =
        expenseRepository.findById(expenseId).orElseThrow(ExpenseNotFoundException::new);

    expenseRepository.delete(expense);
  }

  public List<IncomeResponse> getAllIncomes(Long userId, String month) {

    LocalDateTime start = null;
    LocalDateTime end = null;

    if (month != null) {
      try {
        YearMonth yearMonth = YearMonth.parse(month);
        start = yearMonth.atDay(1).atStartOfDay();
        end = start.plusMonths(1);
      } catch (DateTimeParseException ex) {
        throw new InvalidMonthFormatException(month);
      }
    }

    return incomeRepository.findWithFilters(userId, start, end).stream()
        .map(incomeMapper::toIncomeResponse)
        .toList();
  }

  public IncomeResponse getIncomeById(Long incomeId) {
    Income income = incomeRepository.findById(incomeId).orElseThrow(IncomeNotFoundException::new);

    return incomeMapper.toIncomeResponse(income);
  }

  @Transactional
  public void deleteIncomeById(Long incomeId) {
    Income income = incomeRepository.findById(incomeId).orElseThrow(IncomeNotFoundException::new);

    incomeRepository.delete(income);
  }
}
