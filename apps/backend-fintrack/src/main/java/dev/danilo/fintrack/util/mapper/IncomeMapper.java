package dev.danilo.fintrack.util.mapper;

import dev.danilo.fintrack.dto.response.IncomeResponse;
import dev.danilo.fintrack.entity.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {
  public IncomeResponse toIncomeResponse(Income income) {
    return new IncomeResponse(income.getAmount(), income.getDescription());
  }
}
