package dev.danilo.fintrack.controller;

import dev.danilo.fintrack.dto.request.ExpenseRequest;
import dev.danilo.fintrack.dto.response.ExpenseResponse;
import dev.danilo.fintrack.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody @Valid ExpenseRequest expenseRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                expenseService.createExpense(expenseRequest)
        );
    }

    @GetMapping(params = "month")
    public ResponseEntity<List<ExpenseResponse>> getExpenseByMonthAndCurrentUser(@RequestParam String month) {
        return ResponseEntity.ok(
                expenseService.findExpenseByMonthAndCurrentUser(month)
        );
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getExpensesByCurrentUser() {
        return ResponseEntity.ok(
                expenseService.findExpensesByCurrentUser()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseByIdAndCurrentUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(
                expenseService.findExpenseByIdAndCurrentUser(userId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable("id") Long expenseId,
                                                         @RequestBody @Valid ExpenseRequest expenseRequest) {
        return ResponseEntity.ok(
                expenseService.updateExpense(expenseId, expenseRequest)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") Long expenseId) {
        expenseService.deleteExpenseById(expenseId);

        return ResponseEntity.noContent().build();
    }
}
