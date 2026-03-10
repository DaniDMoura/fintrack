package dev.danilo.fintrack.controller;

import dev.danilo.fintrack.dto.request.IncomeAmountRequest;
import dev.danilo.fintrack.dto.request.IncomeRequest;
import dev.danilo.fintrack.dto.response.IncomeResponse;
import dev.danilo.fintrack.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/incomes")
public class IncomeController {

  private final IncomeService incomeService;

  public IncomeController(IncomeService incomeService) {
    this.incomeService = incomeService;
  }

  @PostMapping
  public ResponseEntity<IncomeResponse> createIncome(
      @RequestBody @Valid IncomeRequest incomeRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(incomeService.createIncome(incomeRequest));
  }

  @GetMapping
  public ResponseEntity<IncomeResponse> getCurrentIncome() {
    return ResponseEntity.ok(incomeService.findCurrentUserIncome());
  }

  @PutMapping
  public ResponseEntity<IncomeResponse> updateCurrentIncome(
      @RequestBody @Valid IncomeRequest incomeRequest) {
    return ResponseEntity.ok(incomeService.updateCurrentUserIncome(incomeRequest));
  }

  @PatchMapping
  public ResponseEntity<IncomeResponse> setCurrentIncomeAmount(
      @RequestBody @Valid IncomeAmountRequest incomeAmountRequest) {
    return ResponseEntity.ok(incomeService.setCurrentUserIncomeAmount(incomeAmountRequest));
  }
}
