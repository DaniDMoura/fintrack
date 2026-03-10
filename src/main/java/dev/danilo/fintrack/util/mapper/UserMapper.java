package dev.danilo.fintrack.util.mapper;

import dev.danilo.fintrack.dto.request.UserRequest;
import dev.danilo.fintrack.dto.response.ExpenseResponse;
import dev.danilo.fintrack.dto.response.IncomeResponse;
import dev.danilo.fintrack.dto.response.UserResponse;
import dev.danilo.fintrack.entity.Income;
import dev.danilo.fintrack.entity.User;
import java.time.YearMonth;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  public UserMapper(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public UserResponse toUserResponse(User user) {
    IncomeResponse incomeResponse = null;

    if (user.getIncome() != null) {
      incomeResponse =
          new IncomeResponse(
              user.getIncome().getLast().getAmount(), user.getIncome().getLast().getDescription());
    }

    List<ExpenseResponse> expenseResponses =
        user.getExpenses() == null
            ? List.of()
            : user.getExpenses().stream()
                .map(
                    expense ->
                        new ExpenseResponse(
                            expense.getId(),
                            expense.getAmount(),
                            expense.getCategory(),
                            expense.getDescription(),
                            expense.getYearMonth(),
                            expense.getCreatedAt()))
                .toList();

    return new UserResponse(user.getId(), user.getEmail(), incomeResponse, expenseResponses);
  }

  public User toUserEntity(UserRequest userRequest) {
    User user = new User();
    user.setEmail(userRequest.email());
    user.setPassword(passwordEncoder.encode(userRequest.password()));

    if (userRequest.income() != null) {
      Income income = new Income();
      income.setAmount(userRequest.income().amount());
      income.setDescription(userRequest.income().description());
      income.setYearMonth(YearMonth.now());
      income.setUser(user);

      user.setIncome(List.of(income));
    }

    return user;
  }
}
