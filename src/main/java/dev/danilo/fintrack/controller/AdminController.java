package dev.danilo.fintrack.controller;

import dev.danilo.fintrack.dto.response.ExpenseResponse;
import dev.danilo.fintrack.dto.response.IncomeResponse;
import dev.danilo.fintrack.dto.response.UserResponse;
import dev.danilo.fintrack.service.AdminService;
import java.time.YearMonth;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    return ResponseEntity.ok(adminService.getAllUsers());
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long userId) {
    return ResponseEntity.ok(adminService.getUserById(userId));
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long userId) {
    adminService.deleteUserById(userId);

    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/users/{id}/role")
  public ResponseEntity<UserResponse> setUserRoleAdmin(@PathVariable("id") Long userId) {
    return ResponseEntity.ok(adminService.setUserRoleAdmin(userId));
  }

  @GetMapping("/expenses")
  public ResponseEntity<List<ExpenseResponse>> getAllExpenses(
      @RequestParam(required = false) Long userId, @RequestParam(required = false) String month) {

    return ResponseEntity.ok(adminService.getAllExpenses(userId, month));
  }

  @GetMapping("/expenses/{id}")
  public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable("id") Long expenseId) {
    return ResponseEntity.ok(adminService.getExpenseById(expenseId));
  }

  @DeleteMapping("/expenses/{id}")
  public ResponseEntity<Void> deleteExpenseById(@PathVariable("id") Long expenseId) {
    adminService.deleteExpenseById(expenseId);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/incomes")
  public ResponseEntity<List<IncomeResponse>> getAllIncomes(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
    return ResponseEntity.ok(adminService.getAllIncomes(userId, yearMonth));
  }

  @GetMapping("/incomes/{id}")
  public ResponseEntity<IncomeResponse> getIncomeById(@PathVariable("id") Long incomeId) {
    return ResponseEntity.ok(adminService.getIncomeById(incomeId));
  }

  @DeleteMapping("/incomes/{id}")
  public ResponseEntity<Void> deleteIncomeById(@PathVariable("id") Long incomeId) {
    adminService.deleteIncomeById(incomeId);

    return ResponseEntity.noContent().build();
  }
}
