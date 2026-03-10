package dev.danilo.fintrack.util.mapper;

import dev.danilo.fintrack.dto.request.IncomeRequest;
import dev.danilo.fintrack.dto.response.IncomeResponse;
import dev.danilo.fintrack.entity.Income;
import dev.danilo.fintrack.entity.User;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

  public IncomeResponse toIncomeResponse(Income income) {
    return new IncomeResponse(income.getAmount(), income.getDescription());
  }

  public Income toIncomeEntity(IncomeRequest incomeRequest, User user) {
    Income income = new Income();
    income.setYearMonth(incomeRequest.yearMonth());
    income.setDescription(incomeRequest.description());
    income.setAmount(incomeRequest.amount());
    income.setUser(user);

    return income;
  }
}
