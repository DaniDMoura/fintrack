package dev.danilo.fintrack.dto.response;

import java.util.List;

public record UserResponse(
    Long id, String email, IncomeResponse income, List<ExpenseResponse> expenses) {}
