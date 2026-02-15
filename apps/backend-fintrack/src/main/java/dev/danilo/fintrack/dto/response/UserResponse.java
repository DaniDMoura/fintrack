package dev.danilo.fintrack.dto.response;

import dev.danilo.fintrack.entity.Expense;
import dev.danilo.fintrack.entity.Income;
import java.util.List;

public record UserResponse(Long id, String email, Income income, List<Expense> expenses) {}
