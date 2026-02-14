package dev.danilo.fintrack.util.mapper;

import dev.danilo.fintrack.dto.request.ExpenseRequest;
import dev.danilo.fintrack.dto.response.ExpenseResponse;
import dev.danilo.fintrack.entity.Expense;
import dev.danilo.fintrack.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    public ExpenseResponse toExpenseResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getCreatedAt()
        );
    }

    public Expense toExpenseEntity(ExpenseRequest expenseRequest, User user) {
        return new Expense(
                user,
                expenseRequest.amount(),
                expenseRequest.category(),
                expenseRequest.description()
        );
    }
}
