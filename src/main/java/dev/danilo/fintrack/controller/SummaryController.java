package dev.danilo.fintrack.controller;

import dev.danilo.fintrack.dto.response.AnnualFinancialSummaryResponse;
import dev.danilo.fintrack.dto.response.MonthlyFinancialSummaryResponse;
import dev.danilo.fintrack.service.SummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/summary")
public class SummaryController {

  private final SummaryService summaryService;

  public SummaryController(SummaryService summaryService) {
    this.summaryService = summaryService;
  }

  @GetMapping("/months")
  public ResponseEntity<MonthlyFinancialSummaryResponse> getSummary(
      @RequestParam(required = false) String month) {
    return ResponseEntity.ok(summaryService.getMonthlyFinancialSummary(month));
  }

  @GetMapping("/annual")
  public ResponseEntity<AnnualFinancialSummaryResponse> getYearSummary(
      @RequestParam(required = false) Integer year) {
    return ResponseEntity.ok(summaryService.getAnnualFinancialSummary(year));
  }
}
